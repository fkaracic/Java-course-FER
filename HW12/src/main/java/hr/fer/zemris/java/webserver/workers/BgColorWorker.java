package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Receives one argument - color and if valid changes color and prints message,
 * else prints fail message.
 * 
 * @author Filip Karacic
 *
 */
public class BgColorWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) {
		String color = context.getParameter("bgcolor");

		boolean validColor = validColor(color);

		if (validColor) {
			context.setPersistentParameter("bgcolor",color);
		}

		String changed = validColor ? "Color has been changed" : "Color has not been changed";

		try {
			context.write("<html>\r\n" + "  <head>\r\n" + "  \r\n" + "    <meta charset=\"utf-8\">\r\n"
					+ "    <title>Color change</title>\r\n" + "    <style type=\"text/css\">\r\n" + "      BODY {\r\n"
					+ "        background-color: #ffff55;\r\n" + "      }\r\n" + "      SPAN.kratica {\r\n"
					+ "        font-style: italic;\r\n" + "      }\r\n" + "    </style>\r\n" + "    \r\n"
					+ "  </head>\r\n" + "  <body>\r\n" + "  \r\n" + "    <h1>" + changed + "</h1>\r\n" + "    \r\n"
					+ "    <ul>\r\n"
					+ "      <li>Back to page <a href=\"/index2.html\" target=\"_self\">(here)</a></li>\r\n"
					+ "    </ul>\r\n" + "  </body>\r\n" + "</html>");
		} catch (Exception e) {
		}

	}

	/**
	 * Returns <code>true</code> if the given {@code String} is valid color
	 * representation (6 hex-digits).
	 * 
	 * @param color
	 *            {@code String} to be checked
	 * 
	 * @return <code>true</code> if the given {@code String} is valid color
	 *         representation (6 hex-digits)
	 */
	private boolean validColor(String color) {
		if(color.length() != 6) return false;
		
		for (char c : color.toCharArray()) {
			char ch = Character.toUpperCase(c);
			if (!(ch >= '0' && ch <= '9') && !(ch >= 'A' && ch <= 'F'))
				return false;
		}

		return true;
	}
}