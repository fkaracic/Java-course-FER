package hr.fer.zemris.java.hw13.servleti;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Represents servlet used for voting about users favourite band among the given
 * bands.
 * 
 * @author Filip Karacic
 *
 */
public class GlasanjeGlasajServlet extends HttpServlet {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected synchronized void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String rezultati = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		Path path = Paths.get(rezultati);

		String definicija = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		Path filePath = Paths.get(definicija);

		if (!Files.exists(path)) {
			path.toFile().createNewFile();
			List<String> lines = new ArrayList<>();

			Files.lines(filePath, StandardCharsets.UTF_8).forEach(line -> lines.add((line.split("\t")[0]) + "\t0"));

			Files.write(path, lines, StandardCharsets.UTF_8);
		}

		String id = req.getParameter("id");

		if (id == null)
			return;

		List<String> lines = new ArrayList<>();

		Files.lines(path, StandardCharsets.UTF_8).forEach(line -> addLine(lines, line, id));

		Files.write(path, lines, StandardCharsets.UTF_8);
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}

	/**
	 * Updating result list by increasing votes number to the voted band.
	 * 
	 * @param lines
	 *            result list
	 * @param line
	 *            one band record
	 * @param id
	 *            identification number of the band
	 */
	private void addLine(List<String> lines, String line, String id) {
		if (line.startsWith(id)) {
			String newLine = id + "\t" + (Integer.parseInt(line.split("\t")[1]) + 1);
			lines.add(newLine);
		} else {
			lines.add(line);
		}
	}
}
