package hr.fer.zemris.java.hw14.servleti;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.model.PollOption;
import hr.fer.zemris.java.hw14.dao.DAOProvider;

/**
 * Represents servlet for the display of the results of voting for a favourite
 * band. Results are displayed in table, as a pie chart, in Microsoft Excel
 * document. Additionally song/s of the leading band/s is/are displayed.
 * 
 * @author Filip Karacic
 *
 */
@WebServlet("/servleti/glasanjeRezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected synchronized void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String pollID = (String) req.getParameter("pollID");

		if (pollID == null) {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
		}

		try {
			List<PollOption> options = DAOProvider.getDao().getPollOptions(Long.parseLong(pollID));
			options.sort((o1, o2) -> Long.compare(o2.getVotes(), o1.getVotes()));
			req.setAttribute("options", options);

			mostPopular(req, options);
		} catch (NumberFormatException e) {
			req.setAttribute("message", "pollID must be a number.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		} catch (SQLException e) {
			req.setAttribute("message", "Error providing voting results.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp?pollID=" + pollID).forward(req, resp);

	}

	/**
	 * Determines most popular options.
	 * 
	 * @param req
	 *            request for this servlet
	 * @param options
	 *            options for the poll
	 */
	private void mostPopular(HttpServletRequest req, List<PollOption> options) {
		List<PollOption> mostPopular = new ArrayList<>();

		long maxVotes = options.get(0).getVotes();

		for (PollOption option : options) {
			if (maxVotes == option.getVotes()) {
				mostPopular.add(option);
			} else {
				break;
			}
		}

		req.setAttribute("popular", mostPopular);

	}
}
