package hr.fer.zemris.java.hw13.servleti;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used for setting background color which will be used for each of web
 * pages in the application.
 * 
 * @author Filip Karacic
 *
 */
public class SetColorServlet extends HttpServlet {

	/**
	 * Colors available.
	 */
	private final static Map<String, String> colors = new HashMap<>();

	static {
		colors.put("white", "#FFFFFF");
		colors.put("green", "#008000");
		colors.put("red", "#FF0000");
		colors.put("cyan", "#00FFFF");
	}

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String color = req.getParameter("color");

		if (color == null || colors.get(color) == null) {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}

		req.getSession().setAttribute("pickedBgCol", colors.get(color));

		resp.getWriter().write("<h1>The color to be used is: " + color + "</h1>");

		resp.getWriter().write("<body bgcolor=" + color + ">");

		resp.getWriter().write("<p><font size=\"5\">Return home: <a href=\"/webapp2/\">HOME</a></font></p>");
	}

}
