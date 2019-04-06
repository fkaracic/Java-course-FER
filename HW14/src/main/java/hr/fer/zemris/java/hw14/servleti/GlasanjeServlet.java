package hr.fer.zemris.java.hw14.servleti;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;
import hr.fer.zemris.java.hw14.dao.DAOProvider;

/**
 * Represents servlet used for voting about users favourite options in the given
 * polls.
 * 
 * @author Filip Karacic
 *
 */
@WebServlet("/servleti/glasanje")
public class GlasanjeServlet extends HttpServlet {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected synchronized void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String pollID = req.getParameter("pollID");

		if (pollID == null) {
			req.setAttribute("message", "Missing pollID.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}

		try {
			List<PollOption> options = DAOProvider.getDao().getPollOptions(Long.parseLong(pollID));

			if (options.isEmpty()) {
				req.setAttribute("message", "There is no option for the poll with the given pollID.");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}
			req.setAttribute("options", options);
			
			Poll poll = DAOProvider.getDao().getPoll(Long.parseLong(pollID));
			req.setAttribute("poll", poll);
			req.getRequestDispatcher("/WEB-INF/pages/glasanjeGlasaj.jsp?pollID=" + pollID).forward(req, resp);

		} catch (NumberFormatException e) {
			req.setAttribute("message", "pollID must be a number.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		} catch (SQLException e) {
			req.setAttribute("message", "Error getting poll.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
	}

}
