package hr.fer.zemris.java.hw13.servleti;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 * Represents servlet for voting results displaying. Results are displayed as
 * pie chart.
 * 
 * @author Filip Karacic
 *
 */
public class GlasanjeGrafikaServlet extends HttpServlet {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String rezultati = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		Path path = Paths.get(rezultati);

		String definicija = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		Path filePath = Paths.get(definicija);

		Map<String, Integer> results = new LinkedHashMap<>();
		if (!Files.exists(path)) {
			List<String> def = Files.readAllLines(filePath, StandardCharsets.UTF_8);

			for (int i = 0, h = def.size(); i < h; i++) {
				String[] attributes = def.get(i).split("\t");
				String name = attributes[1];
				results.put(name, 0);
			}
		} else {
			List<String> rez = Files.readAllLines(path, StandardCharsets.UTF_8);
			List<String> def = Files.readAllLines(filePath, StandardCharsets.UTF_8);

			for (int i = 0, h = rez.size(); i < h; i++) {
				String name = def.get(i).split("\t")[1];

				String[] attributes = rez.get(i).split("\t");
				Integer votes = Integer.parseInt(attributes[1]);

				results.put(name, votes);
			}
		}

		PieChart chart = new PieChart("", results);
		resp.setContentType("image/png");
		try {
			ImageIO.write(chart.image, "png", resp.getOutputStream());
			resp.getOutputStream().flush();
		} catch (Exception e) {
			System.out.println("Mistake: " + e.getMessage());
		} finally {
			resp.getOutputStream().close();
		}
	}

	/**
	 * Represents pie chart of voting results for the favourite band.
	 *
	 */
	private class PieChart {
		/**
		 * Image representing pie chart.
		 */
		BufferedImage image;

		/**
		 * Constructor for this pie chart.
		 * 
		 * @param chartTitle
		 *            title of the chart
		 * @param results
		 *            voting results
		 */
		public PieChart(String chartTitle, Map<String, Integer> results) {
			PieDataset dataset = createDataset(results);
			JFreeChart chart = createChart(dataset, chartTitle);

			image = chart.createBufferedImage(500, 270);
		}

		/**
		 * Creates a sample dataset for this survey.
		 * 
		 * @param results
		 *            voting results
		 *            
		 * @return dataset created
		 */
		private PieDataset createDataset(Map<String, Integer> results) {
			DefaultPieDataset result = new DefaultPieDataset();

			for (Entry<String, Integer> entry : results.entrySet()) {
				result.setValue(entry.getKey(), entry.getValue());
			}

			return result;

		}

		/**
		 * Returns newly created chart.This chart is represented as pie chart.
		 * 
		 * @param dataset
		 *            dataset for this chart
		 * @param title
		 *            title of the chart
		 * 
		 * @return created pie chart
		 */
		private JFreeChart createChart(PieDataset dataset, String title) {

			JFreeChart chart = ChartFactory.createPieChart3D(title, dataset, true, true, false);

			PiePlot3D plot = (PiePlot3D) chart.getPlot();
			plot.setStartAngle(290);
			plot.setDirection(Rotation.CLOCKWISE);
			plot.setForegroundAlpha(0.5f);
			return chart;

		}
	}
}
