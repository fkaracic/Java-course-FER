package hr.fer.zemris.java.hw14.servleti;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOProvider;

/**
 * Represents servlet used for voting about users favourite option in the poll
 * among the given polls and options.
 * 
 * @author Filip Karacic
 *
 */
@WebServlet("/servleti/glasanjeGlasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected synchronized void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String ID = req.getParameter("ID");
		String pollID = (String) req.getParameter("pollID");

		if (ID == null || pollID == null) {
			req.setAttribute("message", "pollID and ID must be given.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}

		try {
			DAOProvider.getDao().addVote(Long.parseLong(ID));
			req.getRequestDispatcher("/servleti/glasanjeRezultati?pollID=" + pollID).forward(req, resp);
		} catch (NumberFormatException e) {
			req.setAttribute("message", "ID must be a number.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		} catch (SQLException e) {
			req.setAttribute("message", "Error voting.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
		}
	}

}
