package hr.fer.zemris.java.hw13.servleti;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Represents servlet for the display of the results of voting for a favourite
 * band. Results are displayed in table, as a pie chart, in Microsoft Excel
 * document. Additionally song/s of the leading band/s is/are displayed.
 * 
 * @author Filip Karacic
 *
 */
public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected synchronized void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Map<String, Integer> results = new LinkedHashMap<>();
		Map<String, String> winnerSongs = new LinkedHashMap<>();

		try {
			displayResults(req, resp, results, winnerSongs);
		} catch (Exception e) {

		}
		req.setAttribute("songs", winnerSongs);
		req.setAttribute("results", results);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);

	}

	/**
	 * Displays voting results for the favourite band
	 * 
	 * @param req
	 *            users request
	 * @param resp
	 *            response to the request
	 * @param results
	 *            results as name and votes
	 * @param winnerSongs
	 *            song of the winning band/s
	 * 
	 * @throws IOException
	 *             if error displaying results occurs
	 */
	private void displayResults(HttpServletRequest req, HttpServletResponse resp, Map<String, Integer> results,
			Map<String, String> winnerSongs) throws IOException {
		String resultings = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		Path path = Paths.get(resultings);

		String definition = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		Path filePath = Paths.get(definition);

		if (!Files.exists(path)) {
			List<String> def = Files.readAllLines(filePath, StandardCharsets.UTF_8);

			for (int i = 0, h = def.size(); i < h; i++) {
				String[] attributes = def.get(i).split("\t");
				String name = attributes[1];
				String song = attributes[2];
				results.put(name, 0);
				winnerSongs.put(name, song);
			}
		} else {
			existingFileResults(path, filePath, results, winnerSongs);
		}

	}

	/**
	 * Displays results from the existing file of results.
	 * 
	 * @param path
	 *            path of the file with the results
	 * @param filePath
	 *            path of the file with the definition
	 * @param results
	 *            voting results
	 * @param winnerSongs
	 *            song of the best band/s
	 * 
	 * @throws IOException
	 *             if error while reading occurs
	 */
	private void existingFileResults(Path path, Path filePath, Map<String, Integer> results,
			Map<String, String> winnerSongs) throws IOException {
		List<String> res = Files.readAllLines(path, StandardCharsets.UTF_8);
		List<String> def = Files.readAllLines(filePath, StandardCharsets.UTF_8);

		Integer maxVotes = null;

		for (int i = 0, h = res.size(); i < h; i++) {
			String name = def.get(i).split("\t")[1];

			String[] attributes = res.get(i).split("\t");
			Integer votes = Integer.parseInt(attributes[1]);

			if (maxVotes == null) {
				maxVotes = votes;
			} else if (maxVotes < votes) {
				maxVotes = votes;
			}

			results.put(name, votes);
		}

		addSongs(res, def, winnerSongs, maxVotes);

	}

	/**
	 * Adding winner's song to the map.
	 * 
	 * @param res
	 *            results of votes
	 * @param def
	 *            definition of bands
	 * @param winnerSongs
	 *            winner song/s
	 * @param maxVotes
	 *            the highest number of votes
	 */
	private void addSongs(List<String> res, List<String> def, Map<String, String> winnerSongs, Integer maxVotes) {
		List<String> winnerID = new ArrayList<>();
		for (int i = 0, h = res.size(); i < h; i++) {
			if (res.get(i).split("\t")[1].equals(maxVotes.toString())) {
				winnerID.add(res.get(i).split("\t")[0]);
			}
		}

		for (int i = 0, h = res.size(); i < h; i++) {
			String[] attributes = def.get(i).split("\t");

			for (int j = 0, k = winnerID.size(); j < k; j++) {
				if (attributes[0].equals(winnerID.get(j))) {
					winnerSongs.put(attributes[1], attributes[2]);
				}
			}
		}

	}
}
