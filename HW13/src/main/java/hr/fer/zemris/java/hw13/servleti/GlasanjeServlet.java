package hr.fer.zemris.java.hw13.servleti;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Represents servlet for voting for the favourite band among the given. Bands
 * in the survey are listed and on click user can vote for any of the bands.
 * 
 * @author Filip Karacic
 *
 */
public class GlasanjeServlet extends HttpServlet {
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Map where band's id is mapped to band's name.
	 */
	private SortedMap<Integer, String> bandNames = new TreeMap<>();
	/**
	 * Map where band's id is mapped to band's song.
	 */
	private SortedMap<Integer, String> bandSongs = new TreeMap<>();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
			String bands = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);

			String[] band = bands.split("(\r)?\n");

			for (String bandRecord : band) {
				String[] idNameSong = bandRecord.split("\t+");
				bandNames.put(Integer.parseInt(idNameSong[0]), idNameSong[1]);
				bandSongs.put(Integer.parseInt(idNameSong[0]), idNameSong[2]);
			}
		} catch (Exception e) {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}

		req.setAttribute("names", bandNames);
		req.setAttribute("songs", bandSongs);

		req.getRequestDispatcher("WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
}
