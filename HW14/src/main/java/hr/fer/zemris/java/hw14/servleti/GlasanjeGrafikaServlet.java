package hr.fer.zemris.java.hw14.servleti;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import hr.fer.zemris.java.hw14.model.PollOption;
import hr.fer.zemris.java.hw14.dao.DAOProvider;

/**
 * Represents servlet for voting results displaying. Results are displayed as
 * pie chart.
 * 
 * @author Filip Karacic
 *
 */
@WebServlet("/servleti/glasanje-grafika")
public class GlasanjeGrafikaServlet extends HttpServlet {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pollID = (String) req.getParameter("pollID");

		List<PollOption> options;
		try {
			options = DAOProvider.getDao().getPollOptions(Long.parseLong(pollID));
		} catch (NumberFormatException e1) {
			return;
		} catch (SQLException e1) {
			return;
		}

		PieChart chart = new PieChart("", options);
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
		private BufferedImage image;

		/**
		 * Image width.
		 */
		private static final int WIDTH = 500;
		/**
		 * Image height.
		 */
		private static final int HEIGHT = 270;
		/**
		 * Angle of the rotation of the pie-charts plot.
		 */
		private static final int angle = 290;

		/**
		 * Constructor for this pie chart.
		 * 
		 * @param chartTitle
		 *            title of the chart
		 * @param options
		 *            voting results
		 */
		public PieChart(String chartTitle, List<PollOption> options) {
			PieDataset dataset = createDataset(options);
			JFreeChart chart = createChart(dataset, chartTitle);

			image = chart.createBufferedImage(WIDTH, HEIGHT);
		}

		/**
		 * Creates a sample dataset for this survey.
		 * 
		 * @param options
		 *            voting results
		 * 
		 * @return dataset created
		 */
		private PieDataset createDataset(List<PollOption> options) {
			DefaultPieDataset result = new DefaultPieDataset();

			for (PollOption option : options) {
				if (option.getVotes() == 0)
					continue;
				result.setValue(option.getTitle(), option.getVotes());
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
			plot.setStartAngle(angle);
			plot.setDirection(Rotation.CLOCKWISE);
			plot.setForegroundAlpha(0.5f);
			return chart;

		}
	}
}
