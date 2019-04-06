package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Program for demonstration of the work.
 * 
 * @author Filip Karacic
 *
 */
public class DemoRequestContext {
	/**
	 * Method called when this program starts.
	 * 
	 * @param args
	 *            command line arguments.
	 */
	public static void main(String[] args) {

		try {
			demo1("primjer1.txt", "ISO-8859-2");
			demo1("primjer2.txt", "UTF-8");
			demo2("primjer3.txt", "UTF-8");
		} catch (IOException e) {
			System.out.println("Error reading files.");
		}
	}

	/**
	 * Method for writing files to output stream.
	 * 
	 * @param filePath path to the file
	 * @param encoding encoding for the text
	 * @throws IOException if error reading file occurs
	 */
	private static void demo1(String filePath, String encoding) throws IOException {
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		RequestContext rc = new RequestContext(os, new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		
		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		
		// Only at this point will header be created and written...
		rc.write("Čevapčići i Šiščevapčići.");
		
		os.close();
	}

	/**
	 * Method for writing files to output stream.
	 * 
	 * @param filePath path to the file
	 * @param encoding encoding for the text
	 * @throws IOException if error reading file occurs
	 */
	private static void demo2(String filePath, String encoding) throws IOException {
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		RequestContext rc = new RequestContext(os, new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		
		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		rc.addRCCookie(new RCCookie("korisnik", "perica", 3600, "127.0.0.1", "/"));
		
		rc.addRCCookie(new RCCookie("zgrada", "B4", null, null, "/"));
		
		// Only at this point will header be created and written...
		rc.write("Čevapčići i Šiščevapčići.");
		os.close();
	}
}