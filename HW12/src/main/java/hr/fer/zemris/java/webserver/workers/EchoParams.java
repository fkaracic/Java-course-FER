package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Represents worker that outputs back to the user the parameters it obtained
 * formatted as an HTML table.
 * 
 * @author Filip Karacic
 *
 */
public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws IOException {
		context.setMimeType("text/html");

		context.write("<html>\r\n" + "<head>\r\n" + "<title>Parameters table</title>\r\n" + "</head>\r\n" + "<body>\r\n"
				+ "<h1>Parameters</h1>\r\n" + "<table>\r\n" + "<thead>\r\n" + "<table border=1>"
				+ "<tr><th>Name</th><th>Value</th></tr>\r\n" + "</thead>\r\n" + "<tbody>");

		for (String parameterName : context.getParameterNames()) {
			String value = context.getParameter(parameterName);
			context.write("<tr><td>" + parameterName + "</td><td>" + value + "</td></tr>");
		}

		context.write("</tbody>\r\n" + "</table>\r\n" + "</body>\r\n" + "</html>");
	}
}