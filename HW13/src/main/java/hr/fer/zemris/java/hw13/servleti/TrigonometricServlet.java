package hr.fer.zemris.java.hw13.servleti;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used for obtaining table of values of trigonometric functions sine
 * and cosine for all integer angles(in degrees) in a range between two
 * parameters.
 * 
 * @author Filip Karacic
 *
 */
public class TrigonometricServlet extends HttpServlet {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String a = req.getParameter("a");
		a = a == null ? "0" : a;

		String b = req.getParameter("b");
		b = b == null ? "360" : b;

		int paramA;
		int paramB;
		try {
			paramA = Integer.parseInt(a);
			paramB = Integer.parseInt(b);
		} catch (NumberFormatException e) {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}

		if (paramA > paramB) {
			int pom = paramA;
			paramA = paramB;
			paramB = pom;
		}

		if (paramB > 720 + paramA) {
			paramB = paramA + 720;
		}

		List<Integer> angles = new ArrayList<>();
		for (int i = paramA; i <= paramB; i++) {
			angles.add(i);
		}

		req.setAttribute("angles", angles);

		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}

}
