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
import hr.fer.zemris.java.hw14.dao.DAOProvider;

/**
 * Represents servlet for voting for the favourite band among the given. Bands
 * in the survey are listed and on click user can vote for any of the bands.
 * 
 * @author Filip Karacic
 *
 */
@WebServlet("/servleti/index.html")
public class AnketeServlet extends HttpServlet {
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			List<Poll> polls = DAOProvider.getDao().getPolls();
			req.setAttribute("polls", polls);
			req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
		} catch (SQLException e) {
			req.setAttribute("message", "Cannot list polls.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp");
		}
	}
}
