package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Object of the class {@code RequestContext} represents context of the user's
 * request (i.e. statuses, types..).
 * 
 * @author Filip Karacic
 *
 */
public class RequestContext {

	/**
	 * Output stream for this context.
	 */
	private OutputStream outputStream;
	/**
	 * Charset for this context.
	 */
	private Charset charset;

	/**
	 * Current encoding for this context.
	 */
	private String encoding;

	/**
	 * Default encoding.
	 */
	private static final String DEFAULT_ENCODING = "UTF-8";

	/**
	 * Current status code.
	 */
	private int statusCode;

	/**
	 * Default status code.
	 */
	private static final int DEFAULT_STATUS_CODE = 200;

	/**
	 * Current status text for this context.
	 */
	private String statusText;

	/**
	 * Default status text.
	 */
	private static final String DEFAULT_STATUS_TEXT = "OK";

	/**
	 * Current mime type for this context.
	 */
	private String mimeType;

	/**
	 * Default mime type.
	 */
	private static final String DEFAULT_MIME_TYPE = "text/html";

	/**
	 * Parameters given for this request.
	 */
	private Map<String, String> parameters;
	/**
	 * Temporary parameters for this request.
	 */
	private Map<String, String> temporaryParameters;
	/**
	 * Persistent parameters for this request.
	 */
	private Map<String, String> persistentParameters;
	/**
	 * Output cookies for this request.
	 */
	private List<RCCookie> outputCookies;
	/**
	 * Header generation indicator.
	 */
	private boolean headerGenerated;

	/**
	 * Dispatcher for this request.
	 */
	private IDispatcher dispatcher;

	/**
	 * Length of the content for this request.
	 */
	private Long contentLength;

	/**
	 * Constructor for this request.
	 * 
	 * @param outputStream
	 *            output stream
	 * @param parameters
	 *            parameters map
	 * @param persistentParameters
	 *            persistent parameters map
	 * @param outputCookies
	 *            output cookies map
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies) {
		this(outputStream, parameters, persistentParameters, outputCookies, null, null);
	}

	/**
	 * Initializes newly created {@code RequestContext} object representing context
	 * of the request.
	 * 
	 * @param outputStream
	 *            output stream
	 * @param parameters
	 *            parameters map
	 * @param persistentParameters
	 *            persistent parameters map
	 * @param outputCookies
	 *            output cookies map
	 * 
	 * @param temporaryParameters
	 *            temporary parameters map
	 * @param dispatcher
	 *            dispatcher for this request
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies,
			Map<String, String> temporaryParameters, IDispatcher dispatcher) {
		this.outputStream = Objects.requireNonNull(outputStream);

		this.parameters = parameters == null ? new HashMap<>() : new HashMap<>(parameters);

		this.persistentParameters = persistentParameters == null ? new HashMap<>() : persistentParameters;

		this.outputCookies = outputCookies == null ? new ArrayList<>() : new ArrayList<>(outputCookies);

		encoding = DEFAULT_ENCODING;
		statusCode = DEFAULT_STATUS_CODE;
		statusText = DEFAULT_STATUS_TEXT;
		mimeType = DEFAULT_MIME_TYPE;

		this.temporaryParameters = temporaryParameters == null ? new HashMap<>() : temporaryParameters;
		this.dispatcher = dispatcher;
	}

	/**
	 * Returns dispatcher for this context
	 * 
	 * @return dispatcher for this context
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}

	/**
	 * Returns parameter value mapped within the given name or <code>null</code> if
	 * there is no value mapped within this name.
	 * 
	 * @param name
	 *            name within parameter value is mapped.
	 * @return parameter value mapped within the given name
	 */
	public String getParameter(String name) {
		return parameters.get(Objects.requireNonNull(name));
	}

	/**
	 * Returns names of the all parameters for this context.
	 * 
	 * @return names of the all parameters for this context
	 */
	public Set<String> getParameterNames() {
		return Collections.unmodifiableSet(parameters.keySet());
	}

	/**
	 * Returns persistent parameter value mapped within the given name or
	 * <code>null</code> if there is no value mapped within this name.
	 * 
	 * @param name
	 *            name within persistent parameter value is mapped
	 * @return persistent parameter value mapped within the given name
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(Objects.requireNonNull(name));
	}

	/**
	 * Returns names of the all persistent parameters for this context.
	 * 
	 * @return names of the all persistent parameters for this context
	 */
	public Set<String> getPersistentParameterNames() {
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}

	/**
	 * Maps the given name to the value of the persistent parameter.
	 * 
	 * @param name
	 *            name for this persistent parameter
	 * @param value
	 *            value of the persistent parameter
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(Objects.requireNonNull(name), Objects.requireNonNull(value));
	}

	/**
	 * Removes the given name and value of the persistent parameter mapped if
	 * present.
	 * 
	 * @param name
	 *            name for this persistent parameter
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(Objects.requireNonNull(name));
	}

	/**
	 * Returns temporary parameter value mapped within the given name or
	 * <code>null</code> if there is no value mapped within this name.
	 * 
	 * @param name
	 *            name within temporary parameter value is mapped
	 * 
	 * @return temporary parameter value mapped within the given name
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(Objects.requireNonNull(name));
	}

	/**
	 * Returns names of the all temporary parameters for this context.
	 * 
	 * @return names of the all temporary parameters for this context
	 */
	public Set<String> getTemporaryParameterNames() {
		return Collections.unmodifiableSet(temporaryParameters.keySet());

	}

	/**
	 * Maps the given name to the value of the temporary parameter.
	 * 
	 * @param name
	 *            name for this temporary parameter
	 * @param value
	 *            value of the temporary parameter
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(Objects.requireNonNull(name), Objects.requireNonNull(value));
	}

	/**
	 * Removes the given name and value of the temporary parameter mapped if
	 * present.
	 * 
	 * @param name
	 *            name for this temporary parameter
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(Objects.requireNonNull(name));

	}

	/**
	 * Charset for this context.
	 * 
	 * @return charset for this context
	 */
	public Charset getCharset() {
		return charset;
	}

	/**
	 * Writes the given bytes to the output stream. Generates header if it has not
	 * been generated.
	 * 
	 * @param data
	 *            bytes to be written.
	 * 
	 * @return {@code RequestContext} object representing context
	 * 
	 * @throws IOException
	 *             if error while writing occurs
	 */
	public RequestContext write(byte[] data) throws IOException {
		if (!headerGenerated) {
			generateHeader();
			headerGenerated = true;
		}

		outputStream.write(data);

		outputStream.flush();

		return this;
	}

	/**
	 * Writes the given bytes for the given length and from the given offset to the
	 * output stream. Generates header if it has not been generated.
	 * 
	 * @param data
	 *            bytes to be written
	 * @param offset
	 *            starting position from which bytes are read
	 * @param len
	 *            length to be written
	 * 
	 * @return {@code RequestContext} object representing context
	 * 
	 * @throws IOException
	 *             if error while writing occurs
	 */
	public RequestContext write(byte[] data, int offset, int len) throws IOException {
		if (!headerGenerated) {
			generateHeader();
			headerGenerated = true;
		}

		outputStream.write(data, offset, len);
		outputStream.flush();

		return this;
	}

	/**
	 * Writes the given text to the output stream. Generates header if it has not
	 * been generated.
	 * 
	 * @param text
	 *            text to be written.
	 * 
	 * @return {@code RequestContext} object representing context
	 * 
	 * @throws IOException
	 *             if error while writing occurs
	 */
	public RequestContext write(String text) throws IOException {
		if (!headerGenerated) {
			generateHeader();
			headerGenerated = true;
		}

		byte[] data = text.getBytes(Charset.forName(encoding));

		outputStream.write(data);
		outputStream.flush();

		return this;
	}

	/**
	 * Generates header for this context.
	 * 
	 * @throws IOException
	 *             if error while writing header occurs
	 */
	private void generateHeader() throws IOException {
		List<String> header = new ArrayList<>();

		header.add("HTTP/1.1 " + statusCode + " " + statusText + "\r\n");

		charset = Charset.forName(encoding);

		String contentType = mimeType.startsWith("text/") ? mimeType + "; " + "charset=" + encoding : mimeType;
		header.add("Content-Type: " + contentType + "\r\n");

		if (contentLength != null) {
			header.add("Content-Length: " + contentLength.longValue() + "\r\n");
		}

		if (!outputCookies.isEmpty()) {
			String cookieLine = cookiesToString(outputCookies);
			header.add("Set-Cookie: " + cookieLine + "\r\n");
		}

		header.add("\r\n");

		for (String line : header) {
			outputStream.write(line.getBytes(StandardCharsets.ISO_8859_1));
		}

		outputStream.flush();
	}

	/**
	 * Returns string representation of the cookie.
	 * 
	 * @param cookies
	 *            cookies to be represented as {@code String}
	 * 
	 * @return string representation of the cookie
	 */
	private String cookiesToString(List<RCCookie> cookies) {
		StringBuilder cookieLine = new StringBuilder();
		for (RCCookie cookie : cookies) {

			String nameValue = cookie.getName() + "=" + cookie.getValue() + "; ";

			String domain = cookie.getDomain() == null ? "" : "Domain=" + cookie.getDomain() + "; ";
			String path = cookie.getPath() == null ? "" : "Path=" + cookie.getPath() + "; ";
			String maxAge = cookie.getMaxAge() == null ? "" : "Max-Age=" + cookie.getMaxAge() + "; ";

			cookieLine.append(nameValue + domain + path + maxAge);
		}
		
		cookieLine.append("HttpOnly");
		
		return cookieLine.toString();
	}

	/**
	 * Sets encoding for this context to the given value.
	 * 
	 * @param encoding
	 *            value to be set
	 * 
	 * @throws IllegalStateException
	 *             if header has been generated
	 */
	public void setEncoding(String encoding) {
		if (headerGenerated)
			throw new IllegalStateException("Header is already generated. Cannot change encoding.");

		this.encoding = encoding;
	}

	/**
	 * Sets status code for this context to the given value.
	 * 
	 * @param statusCode
	 *            value to be set
	 * 
	 * @throws IllegalStateException
	 *             if header has been generated
	 */
	public void setStatusCode(int statusCode) {
		if (headerGenerated)
			throw new IllegalStateException("Header is already generated. Cannot change status code.");

		this.statusCode = statusCode;

	}

	/**
	 * Sets status text for this context to the given value.
	 * 
	 * @param statusText
	 *            value to be set
	 * 
	 * @throws IllegalStateException
	 *             if header has been generated
	 */
	public void setStatusText(String statusText) {
		if (headerGenerated)
			throw new IllegalStateException("Header is already generated. Cannot change status text.");

		this.statusText = statusText;
	}

	/**
	 * Sets mime type for this context to the given value.
	 * 
	 * @param mimeType
	 *            value to be set
	 * 
	 * @throws IllegalStateException
	 *             if header has been generated
	 */
	public void setMimeType(String mimeType) {
		if (headerGenerated)
			throw new IllegalStateException("Header is already generated. Cannot change mime.");

		this.mimeType = mimeType;
	}

	/**
	 * Adds the given cookie to the list of cookies for this context.
	 * 
	 * @param cookie
	 *            cookie to be added.
	 * 
	 * @throws IllegalStateException
	 *             if header has been generated
	 */
	public void addRCCookie(RCCookie cookie) {
		if (headerGenerated)
			throw new IllegalStateException("Header is already generated. Cannot add RCCookie.");

		outputCookies.add(Objects.requireNonNull(cookie));
	}

	/**
	 * Removes the given cookie from the list of cookies for this context.
	 * 
	 * @param cookie
	 *            cookie to be removed.
	 * 
	 * @throws IllegalStateException
	 *             if header has been generated
	 */
	public void removeRCCookie(RCCookie cookie) {
		if (headerGenerated)
			throw new IllegalStateException("Header is already generated. Cannot remove RCCookie.");

		outputCookies.remove(Objects.requireNonNull(cookie));
	}

	/**
	 * Sets content length to the given value if header hasn't been generated.
	 * 
	 * @param contentLength
	 *            length to be set
	 * 
	 * @throws IllegalStateException
	 *             if header has been generated
	 */
	public void setContentLength(Long contentLength) {
		if (headerGenerated)
			throw new IllegalStateException("Header is already generated. Cannot change content length.");

		this.contentLength = contentLength;
	}

	/**
	 * Represents cookie for this request context.
	 *
	 */
	public static class RCCookie {
		/**
		 * Name of the cookie.
		 */
		private String name;
		/**
		 * Value of the cookie.
		 */
		private String value;
		/**
		 * Domain of the cookie.
		 */
		private String domain;
		/**
		 * Path of the cookie.
		 */
		private String path;
		/**
		 * Max age of the cookie.
		 */
		private Integer maxAge;

		/**
		 * Initializes newly created cookie.
		 * 
		 * @param name
		 *            name of the cookie
		 * @param value
		 *            value of the cookie
		 * @param maxAge
		 *            max age of the cookie
		 * @param domain
		 *            domain of the cookie
		 * @param path
		 *            path of the cookie
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			this.name = Objects.requireNonNull(name);
			this.value = Objects.requireNonNull(value);
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}

		/**
		 * Returns name of this cookie.
		 * 
		 * @return name of this cookie
		 */
		public String getName() {
			return name;
		}

		/**
		 * Returns value of this cookie.
		 * 
		 * @return value of this cookie
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Returns domain of this cookie.
		 * 
		 * @return domain of this cookie
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * Returns path of this cookie.
		 * 
		 * @return path of this cookie
		 */
		public String getPath() {
			return path;
		}

		/**
		 * Returns max age of this cookie.
		 * 
		 * @return max age of this cookie
		 */
		public Integer getMaxAge() {
			return maxAge;
		}

	}
}
