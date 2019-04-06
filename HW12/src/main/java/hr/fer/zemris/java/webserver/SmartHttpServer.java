package hr.fer.zemris.java.webserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Represents HTTP server. Allowed version are 'HTTP/1.0' and 'HTTP/1.1' and
 * allowed method is 'GET'. Server provides work with workers, scripts, HTML
 * text...
 * <p>
 * Program requires one argument from command line: configuration file name.
 * Server is powered until user enters 'exit' when server is shut down and all
 * of the sessions and data are lost.
 * 
 * @author Filip Karacic
 *
 */
public class SmartHttpServer {

	/**
	 * Address of the server.
	 */
	private String address;
	/**
	 * Server's domain name.
	 */
	private String domainName;
	/**
	 * Port on which requests are accepted.
	 */
	private int port;
	/**
	 * Number of working threads.
	 */
	private int workerThreads;
	/**
	 * Session timeout.
	 */
	private int sessionTimeout;
	/**
	 * Map of mime types.
	 */
	private Map<String, String> mimeTypes = new HashMap<String, String>();
	/**
	 * Server thread.
	 */
	private ServerThread serverThread = new ServerThread();
	/**
	 * Executor service that accepts work.
	 */
	private ExecutorService threadPool;
	/**
	 * Root path of the files.
	 */
	private Path documentRoot;
	/**
	 * Server power indicator.
	 */
	private boolean stopServerThread;
	/**
	 * Map of the workers.
	 */
	private Map<String, IWebWorker> workersMap;
	/**
	 * Sessions for this server.
	 */
	private Map<String, SessionMapEntry> sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();
	/**
	 * Random number generator used for session ID generation.
	 */
	private Random sessionRandom = new Random();

	/**
	 * Thread that removes expired sessions every five minutes.
	 */
	private CacheThread cacheThread = new CacheThread();

	/**
	 * Length of the session ID.
	 */
	private final static int SID_LENGTH = 20;

	/**
	 * Initializes newly created {@code SmartHttpServer} object representing http
	 * server and prepares server for the work.
	 * 
	 * @param configFileName
	 *            name of the configuration file containing required data.
	 */
	public SmartHttpServer(String configFileName) {
		Path path = Paths.get(configFileName);

		try {
			initialization(path);
			cacheThread.start();
		} catch (Exception e) {
			throw new IllegalStateException("Error loading configuration files.");
		}
	}

	/**
	 * Initializes properties.
	 * 
	 * @param path
	 *            path of the configuration file.
	 * 
	 * @throws IOException
	 *             if error while reading files occurs.
	 */
	private void initialization(Path path) throws IOException {
		Properties property = new Properties();
		property.load(Files.newInputStream(path));

		address = property.getProperty("server.address");
		domainName = property.getProperty("server.domainName");
		port = Integer.parseInt(property.getProperty("server.port"));
		workerThreads = Integer.parseInt(property.getProperty("server.workerThreads"));
		sessionTimeout = Integer.parseInt(property.getProperty("session.timeout"));
		documentRoot = Paths.get(property.getProperty("server.documentRoot"));

		initializeMimeTypes(property);
		initializeWorkers(property);

	}

	/**
	 * Initializes workers from the given property.
	 * 
	 * @param property
	 *            property from configuration file
	 * 
	 * @throws IOException
	 *             if error while reading occurs.
	 */
	private void initializeWorkers(Properties property) throws IOException {
		Properties propertie3 = new Properties();
		propertie3.load(Files.newInputStream(Paths.get(property.getProperty("server.workers"))));

		createWorkers(propertie3);
	}

	/**
	 * Initializes mimeTypes from the given property.
	 * 
	 * @param property
	 *            property from configuration file
	 * 
	 * @throws IOException
	 *             if error while reading occurs.
	 */
	private void initializeMimeTypes(Properties property) throws IOException {
		Properties propertie2 = new Properties();
		propertie2.load(Files.newInputStream(Paths.get(property.getProperty("server.mimeConfig"))));

		for (Object objectKey : propertie2.keySet()) {
			String key = (String) objectKey;
			mimeTypes.put(key, propertie2.getProperty(key));
		}

	}

	/**
	 * Initializes workers from the given property.
	 * 
	 * @param property
	 *            property from configuration file
	 * 
	 * @throws IOException
	 *             if error while reading occurs.
	 */
	private void createWorkers(Properties property) throws IOException {
		workersMap = new HashMap<>();

		for (String name : property.stringPropertyNames()) {
			String fqcn = property.getProperty(name);

			if (workersMap.containsKey(name))
				throw new IllegalArgumentException("Cannot have two exact same path.");

			try {
				Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
				Object newObject = referenceToClass.getDeclaredConstructor().newInstance();
				IWebWorker iww = (IWebWorker) newObject;
				workersMap.put(name, iww);
			} catch (Exception e) {
				throw new IOException(e);
			}

		}

	}

	/**
	 * Returns the address of this server.
	 * 
	 * @return the address of this server
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Represents one session entry containing session id, host, validation time and
	 * data important for the session.
	 *
	 */
	private static class SessionMapEntry {
		/**
		 * Session ID.
		 */
		private String sid;
		/**
		 * Host for this session.
		 */
		private String host;
		/**
		 * Validation time in milliseconds.
		 */
		private long validUntil;
		/**
		 * Map with the users data for this session.
		 */
		private Map<String, String> map;

		/**
		 * Initializes newly created session.
		 * 
		 * @param sid
		 *            session id
		 * @param host
		 *            host for this session
		 * @param validUntil
		 *            validation time
		 */
		public SessionMapEntry(String sid, String host, long validUntil) {
			setSid(sid);
			this.host = host;
			this.validUntil = validUntil;

			map = new ConcurrentHashMap<>();
		}

		/**
		 * Sets session id to the given value.
		 * 
		 * @param sid
		 *            value to be set
		 */
		public void setSid(String sid) {
			this.sid = sid;
		}
	}

	/**
	 * Starts server thread if not alive and creates a thread pool with the given
	 * number of threads.
	 */
	protected synchronized void start() {
		if (!serverThread.isAlive()) {
			serverThread.start();
		}

		threadPool = Executors.newFixedThreadPool(workerThreads, new ThreadFactory() {

			@Override
			public Thread newThread(Runnable arg0) {
				Thread thread = new Thread(arg0);
				thread.setDaemon(true);

				return thread;
			}
		});
	}

	/**
	 * Stops the server thread and shuts down the thread pool.
	 */
	protected synchronized void stop() {
		stopServerThread = true;

		threadPool.shutdown();
	}

	/**
	 * Represents server thread that accepts requests and submit it to thread pool
	 * for execution.
	 *
	 */
	protected class ServerThread extends Thread {
		/**
		 * Ensures that call to accept() for this ServerSocket will block for only this
		 * amount of time.
		 */
		private final static int TIMEOUT = 3 * 1000;

		@Override
		public void run() {

			try {
				ServerSocket serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress(port));
				serverSocket.setSoTimeout(TIMEOUT);
				while (true) {
					if (stopServerThread)
						break;

					try {
						Socket client = serverSocket.accept();
						ClientWorker cw = new ClientWorker(client);
						threadPool.submit(cw);
					} catch (IOException e) {
					}
				}

				try {
					serverSocket.close();
				} catch (IOException e) {
				}
			} catch (IOException e) {
				 throw new IllegalStateException(e);
			}
		}
	}

	/**
	 * Represents cache thread that removes expired sessions every five minutes.
	 *
	 */
	protected class CacheThread extends Thread {
		/**
		 * Interval between two cache removal in milliseconds.
		 */
		static final int interval = 300 * 1000;

		/**
		 * Initializes newly created thread.
		 */
		public CacheThread() {
			setDaemon(true);
		}

		@Override
		public void run() {

			while (true) {
				if (stopServerThread)
					break;

				try {
					Thread.sleep(interval);
					removeOldSessions();
				} catch (InterruptedException e) {
					continue;
				}
			}
		}

		/**
		 * Removes expired sessions.
		 */
		private void removeOldSessions() {
			sessions.entrySet().removeIf(entry -> entry.getValue().validUntil <= System.currentTimeMillis());
		}
	}

	/**
	 * Represents client worker that processes user's requests.
	 *
	 */
	private class ClientWorker implements Runnable, IDispatcher {
		/**
		 * Client socket.
		 */
		private Socket csocket;
		/**
		 * Input stream.
		 */
		private PushbackInputStream istream;
		/**
		 * Output stream.
		 */
		private OutputStream ostream;
		/**
		 * Version of the request.
		 */
		private String version;
		/**
		 * Method of the request.
		 */
		private String method;
		/**
		 * Host of the request.
		 */
		private String host;
		/**
		 * Parameters of the request.
		 */
		private Map<String, String> params = new HashMap<String, String>();
		/**
		 * Temporary parameters of the request.
		 */
		private Map<String, String> tempParams = new HashMap<String, String>();
		/**
		 * Persistent parameters of the request.
		 */
		private Map<String, String> permPrams;
		/**
		 * Output cookies for this request.
		 */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		/**
		 * Session id.
		 */
		private String SID;
		/**
		 * Context of the web server.
		 */
		private RequestContext context;

		/**
		 * Initializes newly created client worker.
		 * 
		 * @param csocket
		 *            client socket
		 * 
		 * @throws NullPointerException
		 *             if the given client socket is <code>null</code>
		 */
		public ClientWorker(Socket csocket) {
			this.csocket = Objects.requireNonNull(csocket);
		}

		/**
		 * Parse parameters from the given request.
		 * 
		 * @param parameters
		 *            line of parameters
		 */
		private void parseParameters(String parameters) {
			String[] splitParameters = parameters.split("&");

			for (int i = 0; i < splitParameters.length; i++) {
				if (!splitParameters[i].contains("=")) {
					params.put(splitParameters[i], null);
				} else if (splitParameters[i].matches("[^=]+=")) {
					params.put(splitParameters[i].replace("=", ""), "");
				} else if (splitParameters[i].matches("[^=]+=[^=]+")) {
					String[] keyAndValue = splitParameters[i].split("=");
					params.put(keyAndValue[0], keyAndValue[1]);
				}

			}

		}

		/**
		 * Writes error message to the output stream.
		 * 
		 * @param cos
		 *            output stream
		 * @param statusCode
		 *            status code
		 * @param statusText
		 *            status text
		 */
		private void sendError(OutputStream cos, int statusCode, String statusText) {
			try {
				cos.write((version + " " + statusCode + " " + statusText + "\r\n" + "Server: simple java server\r\n"
						+ "Content-Type: text/plain;charset=UTF-8\r\n" + "Content-Length: 0\r\n"
						+ "Connection: close\r\n" + "\r\n").getBytes(StandardCharsets.US_ASCII));

				cos.flush();
			} catch (IOException e) {
			}

		}

		/**
		 * Reads request and returns header as a list of lines.
		 * 
		 * @param is
		 *            input stream
		 * 
		 * @return header as a list of lines
		 * @throws IOException
		 * 
		 *             if error reading occurs
		 */
		private List<String> readRequest(InputStream is) throws IOException {

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
			l: while (true) {
				int b = is.read();
				if (b == -1)
					return null;
				if (b != 13) {
					bos.write(b);
				}

				switch (state) {
				case 0: if (b == 13) { state = 1;} else if (b == 10) { state = 4; }
						break;
				case 1: if (b == 10) {state = 2;} else { state = 0; }
						break;
				case 2: if (b == 13) {state = 3;} else {state = 0;}
						break;
				case 3: if (b == 10) { break l; } else {state = 0;}
						break;
				case 4: if (b == 10) { break l;} else { state = 0;}
						break;
				}
			}

			String header = new String(bos.toByteArray(), StandardCharsets.US_ASCII);
			return extractHeaders(header);
		}

		/**
		 * Extracts headers from the given {@code String}.
		 * 
		 * @param requestHeader
		 *            header
		 * 
		 * @return list representing lines of the header
		 */
		private List<String> extractHeaders(String requestHeader) {
			List<String> headers = new ArrayList<String>();

			String currentLine = null;
			for (String s : requestHeader.split("\n")) {
				if (s.isEmpty())
					break;
				char c = s.charAt(0);

				if (c == 9 || c == 32) {
					currentLine += s;
				} else {
					if (currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}

			if (!currentLine.isEmpty()) {
				headers.add(currentLine);
			}

			return headers;
		}

		@Override
		public void run() {

			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = csocket.getOutputStream();
			} catch (IOException e) {
			}

			List<String> headers = validateHeader();

			if (headers == null)
				return;

			initializeHost(headers);

			checkSession(headers);

			String path = extractPathAndParameters(headers);

			try {
				internalDispatchRequest(path, true);
			} catch (Exception e1) {
			} finally {
				try {
					csocket.close();
				} catch (IOException e) {
				}
			}
		}

		/**
		 * Extracts path and parameters from the first line.
		 * 
		 * @param headers
		 *            list of header line
		 * 
		 * @return extracted path
		 */
		private String extractPathAndParameters(List<String> headers) {
			String[] firstLine = headers.get(0).split(" ");
			String requestedPath = firstLine[1];
			String path;

			if (requestedPath.contains("?")) {
				String[] pathAndParameters = requestedPath.split("\\?");

				path = pathAndParameters[0];
				
				if(pathAndParameters.length == 1) return path;
				
				String parameters = pathAndParameters[1];

				parseParameters(parameters);
			} else {
				path = requestedPath;
			}

			return path;
		}

		/**
		 * Initializes host from the given header. If there is no 'Host' line, host is
		 * set to domain name.
		 * 
		 * @param headers
		 *            list representing lines of header
		 */
		private void initializeHost(List<String> headers) {
			for (String header : headers) {
				if (header.startsWith("Host:")) {
					String[] separated = header.split(" ");
					String host = separated[1];

					if (separated[1].matches(".+:\\d+")) {
						host = separated[1].substring(0, separated[1].indexOf(":"));
					}

					this.host = host;
				}
			}

			if (host == null) {
				host = domainName;
			}

		}

		/**
		 * Validates and initializes header and returns it as a list of {@code String}
		 * 
		 * @return header as list of {@code String} or <code>null</code> if invalid
		 *         header.
		 */
		private List<String> validateHeader() {
			List<String> headers = null;
			try {
				headers = readRequest(istream);
			} catch (IOException e) {
				return null;
			}

			if (headers == null) {
				sendError(ostream, 400, "Bad request");
				return null;
			}

			String[] firstLine = headers.isEmpty() ? null : headers.get(0).split(" ");
			if (firstLine == null || firstLine.length != 3) {
				sendError(ostream, 400, "Bad request");
				return null;
			}

			method = firstLine[0].toUpperCase();
			if (!method.equals("GET")) {
				sendError(ostream, 405, "Method Not Allowed");
				return null;
			}

			String version = firstLine[2].toUpperCase();
			if (!version.equals("HTTP/1.1") && !version.equals("HTTP/1.0")) {
				sendError(ostream, 505, "HTTP Version Not Supported");
				return null;
			}

			this.version = version;

			return headers;
		}

		/**
		 * Searches for 'Cookie' line in the given header lines and according to it
		 * creates new session or updates the old one.
		 * 
		 * @param headers
		 *            list of header lines
		 */
		private void checkSession(List<String> headers) {
			String sidCandidate = null;
			for (String line : headers) {
				if (!line.startsWith("Cookie:"))
					continue;

				String cookieList = line.substring(line.indexOf(" ") + 1);

				String[] cookies = cookieList.split(";");

				for (String cookie : cookies) {
					String[] nameAndValues = cookie.split("=");
					if (nameAndValues[0].equals("sid")) {
						sidCandidate = nameAndValues[1];
					}
				}
			}

			checkSidCandidate(sidCandidate);

		}

		/**
		 * Validates session id candidate and according to it creates new session or
		 * updates the old one.
		 * 
		 * @param sidCandidate
		 *            candidate for the session id
		 */
		private void checkSidCandidate(String sidCandidate) {
			if (sidCandidate == null) {
				newEntry();
			} else {

				SessionMapEntry entry = sessions.get(sidCandidate);
				if (entry == null) {
					newEntry();
				} else {

					if (!entry.host.equals(host)) {
						newEntry();
					} else {
						if (entry.validUntil <= System.currentTimeMillis()) {
							sessions.remove(sidCandidate);
							newEntry();
						} else {
							entry.sid = sidCandidate;
							SID = entry.sid;
							permPrams = entry.map;
							entry.validUntil = (System.currentTimeMillis() + sessionTimeout * 1000);
						}
					}
				}
			}

		}

		/**
		 * Creates new session entry.
		 */
		public void newEntry() {
			SID = generateSid();

			SessionMapEntry entry = new SessionMapEntry(SID, host, System.currentTimeMillis() + sessionTimeout * 1000);
			permPrams = new ConcurrentHashMap<>();
			entry.map = permPrams;

			sessions.put(SID, entry);

			outputCookies.add(new RCCookie("sid", SID, null, host, "/"));
		}

		/**
		 * Returns new session id randomly generated represented as twenty upper case
		 * letters of English alphabet.
		 * 
		 * @return randomly generated session ID
		 */
		private String generateSid() {
			StringBuilder builder = new StringBuilder();

			for (int i = 0; i < SID_LENGTH; i++) {
				builder.append((char) (sessionRandom.nextInt('Z' - 'A') + 'A'));
			}

			return builder.toString();
		}

		/**
		 * Processes dispatch request. If {@code directCall} is <code>true</code>
		 * private files cannot be accessed.
		 * 
		 * @param urlPath
		 *            path of the url
		 * @param directCall
		 *            indicator of the direct call
		 * 
		 * @throws Exception
		 *             if error occurs while processing request
		 */
		public void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {

			if (urlPath.startsWith("/private") && directCall) {
				sendError(ostream, 404, "Page not found");
				return;
			}

			if (urlPath.matches("/ext/[A-Za-z]+")) {
				extWorker(urlPath);
				return;
			}

			if (checkWorker(urlPath))
				return;

			Path filePath = documentRoot.resolve(urlPath.substring(1)).toAbsolutePath();

			if (!filePath.startsWith(documentRoot)) {
				sendError(ostream, 403, "Forbidden");
				return;
			}

			if (!Files.isRegularFile(filePath) || !Files.isReadable(filePath)) {
				sendError(ostream, 404, "File Not Found");
				return;
			}

			if (urlPath.endsWith(".smscr")) {
				script(filePath);

				return;
			}

			writeOther(urlPath, filePath);

		}

		/**
		 * Processes request that uses script.
		 * 
		 * @param filePath
		 *            path of the file
		 * 
		 * @throws IOException
		 *             if error while reading or writing occurs
		 */
		private void script(Path filePath) throws IOException {
			String documentBody = new String(Files.readAllBytes(filePath), StandardCharsets.UTF_8);

			try {
				new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
						new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this)).execute();
			} catch (Exception e) {
				sendError(ostream, 404, "Bad request");
			}

		}

		/**
		 * Processes request that is defined by the worker.
		 * 
		 * @param urlPath
		 *            path of the url
		 * 
		 * @throws Exception
		 *             if error while reading or writing occurs
		 */
		private void extWorker(String urlPath) throws Exception {
			String fqcn = "hr.fer.zemris.java.webserver.workers.";
			String className = urlPath.substring(5);
			fqcn += className;

			Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
			Object newObject = referenceToClass.getDeclaredConstructor().newInstance();
			IWebWorker iww = (IWebWorker) newObject;

			if (context == null) {
				context = new RequestContext(ostream, params, permPrams, outputCookies);
			}

			iww.processRequest(context);
			return;

		}

		/**
		 * Checks if worker is requested with the given url path. If worker is requested
		 * processes request and returns <code>true</code>.
		 * 
		 * @param urlPath
		 *            path of the url
		 * @return <code>true</code> if worker is requested in the given request
		 * 
		 * @throws Exception
		 *             if error while reading or writing occurs
		 */
		private boolean checkWorker(String urlPath) throws Exception {
			IWebWorker worker = workersMap.get(urlPath);

			if (worker != null) {
				if (context == null) {
					context = new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this);
				}

				worker.processRequest(context);
				return true;
			}

			return false;
		}

		/**
		 * Writes file that does not require worker and is not script.
		 * 
		 * @param urlPath
		 *            path of the url
		 * @param filePath
		 *            file path
		 * 
		 * @throws IOException
		 *             if error while reading or writing occurs
		 */
		private void writeOther(String urlPath, Path filePath) throws IOException {
			String extension = urlPath.substring(urlPath.indexOf(".") + 1);

			String mimeType = mimeTypes.get(extension);

			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}

			if (context == null) {
				long length = filePath.toFile().length();
				context = new RequestContext(ostream, params, permPrams, outputCookies);
				context.setContentLength(length);
			}

			context.setMimeType(mimeType);

			try (InputStream fis = Files.newInputStream(filePath)) {
				byte[] buff = new byte[1024];
				while (true) {
					int r = fis.read(buff);

					if (r < 1)
						break;

					context.write(buff, 0, r);
				}
			}

		}

		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			if (context == null) {
				context = new RequestContext(ostream, params, permPrams, outputCookies);
			}

			internalDispatchRequest(urlPath, false);

		}
	}

	/**
	 * Method called when program starts. Expects one line argument: configuration
	 * file name.
	 * 
	 * @param args
	 *            command line arguments.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Expected configuration file name.");
			return;
		}

		SmartHttpServer server = new SmartHttpServer("./config/server.properties");
		server.start();

		System.out.println("Server is started. Enter 'exit' if you want to shut down server.");
		System.out.println("Any other entered sequence will be disregarded.");

		try (Scanner scanner = new Scanner(System.in)) {
			while (true) {
				System.out.print("> ");
				String line = scanner.nextLine();

				if (line.equalsIgnoreCase("exit")) {
					System.out.println("All sesions and data will be lost.");

					String answer = null;
					while (true) {
						System.out.print("Are you sure? [Y, N] > ");

						answer = scanner.nextLine();

						if (answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("N"))
							break;

						System.out.println("Please enter Y-yes or N-no.");
					}

					if (answer.equalsIgnoreCase("N"))
						continue;

					break;
				}

				System.out.println("Only 'exit' command allowed.");
			}
		}

		System.out.println("Exiting server...");
		server.stop();
	}

}
