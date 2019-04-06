package hr.fer.zemris.java.scripts.demo;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Program reads from 'webroot/scripts/osnovni.smscr' and produces output to the
 * standard output.
 * 
 * @author Filip Karacic
 *
 */
public class OsnovniDemo {

	/**
	 * Method called when program starts.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		String documentBody;
		try {
			documentBody = new String(Files.readAllBytes(Paths.get("webroot/scripts/osnovni.smscr")),
					StandardCharsets.UTF_8);

			Map<String, String> parameters = new HashMap<String, String>();
			Map<String, String> persistentParameters = new HashMap<String, String>();

			List<RCCookie> cookies = new ArrayList<RCCookie>();

			// create engine and execute it
			new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
					new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();
		} catch (Exception e) {
			System.out.println("Error!");
		}
	}
}
