package hr.fer.zemris.java.hw13.servleti;

import java.awt.image.BufferedImage;
import java.io.IOException;

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
 * Servlet used to present 'OS usage' represented by the survey conducted on
 * hundred people. Results are presented by the pie chart.
 * 
 * @author Filip Karacic
 *
 */
public class ReportServlet extends HttpServlet {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PieChart chart = new PieChart("Operating system usage.");
		resp.setContentType("image/png");
		try {
			ImageIO.write(chart.image, "png", resp.getOutputStream());
		} catch (Exception e) {
			System.out.println("Mistake: " + e.getMessage());
		} finally {
			resp.getOutputStream().close();
		}
	}

	/**
	 * Represents pie chart of the 'OS usage' survey conducted on hundred people.
	 *
	 */
	private class PieChart {
		/**
		 * Image representing pie chart for this survey.
		 */
		BufferedImage image;

		/**
		 * Constructor for creating pie chart for this survey.
		 * 
		 * @param chartTitle
		 *            title of the chart
		 */
		public PieChart(String chartTitle) {
			PieDataset dataset = createDataset();
			JFreeChart chart = createChart(dataset, chartTitle);

			image = chart.createBufferedImage(500, 270);
		}

		/**
		 * Returns newly created pie chart dataset.
		 * 
		 * @return newly created pie chart dataset
		 */
		private PieDataset createDataset() {
			DefaultPieDataset result = new DefaultPieDataset();
			result.setValue("Linux", 29);
			result.setValue("Mac", 20);
			result.setValue("Windows", 51);
			return result;

		}

		/**
		 * Returns created chart represented by pie chart used for showing survey
		 * results.
		 * 
		 * @param dataset
		 *            chart's dataset
		 * @param title
		 *            title of the chart
		 * 
		 * @return created chart represented by pie chart used for showing survey
		 *         results
		 */
		private JFreeChart createChart(PieDataset dataset, String title) {

			JFreeChart chart = ChartFactory.createPieChart3D(title,
					dataset,
					true,
					true, false);

			PiePlot3D plot = (PiePlot3D) chart.getPlot();
			plot.setStartAngle(290);
			plot.setDirection(Rotation.CLOCKWISE);
			plot.setForegroundAlpha(0.5f);
			return chart;

		}
	}

}
