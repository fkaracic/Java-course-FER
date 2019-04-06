package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Program for bar chart data display. User must provide only one argument: path
 * to the file containing relevant informations about bar chart. First six lines
 * are red and if every is valid displaying process is started.
 * 
 * @author Filip Karacic
 *
 */
public class BarChartDemo extends JFrame {
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Width of the window.
	 */
	private static final int WIDTH = 700;
	/**
	 * Height of the window.
	 */
	private static final int HEIGHT = 500;

	/**
	 * Constructor that initializes newly created object.
	 * 
	 * @param barChartComponent
	 *            component with the displayed data
	 * @param path
	 *            path to the file with data to be shown
	 * 
	 * @throws NullPointerException
	 *             if the given component is <code>null</code>
	 */
	public BarChartDemo(JComponent barChartComponent, String path) {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		setLocation(20, 20);
		setSize(WIDTH, HEIGHT);
		setMinimumSize(new Dimension(WIDTH + 100, HEIGHT - 100));
		
		add(Objects.requireNonNull(barChartComponent));
		JPanel panel = new JPanel();

		JLabel pathLabel = new JLabel(path);
		panel.add(pathLabel);
		add(panel, BorderLayout.PAGE_START);

		setVisible(true);
	}

	/**
	 * Method called when program starts.
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Must give one argument representing a path to the file with chart description.");
			return;
		}

		BarChart barChart = extractInfo(args[0]);

		if (barChart == null)
			return;

		SwingUtilities.invokeLater(() -> {
			JComponent barChartComponent = new BarChartComponent(barChart);
			new BarChartDemo(barChartComponent, Paths.get(args[0]).toAbsolutePath().toString());
		});

	}

	/**
	 * Returns newly created {@code BarChart} object, or <code>null</code> if error
	 * occurs.
	 * 
	 * @param path
	 *            path to the file with the data.
	 * 
	 * @return newly created {@code BarChart} object, or <code>null</code> if error
	 *         occurs.
	 */
	private static BarChart extractInfo(String path) {
		BarChart barChart;
		try (Scanner scanner = new Scanner(Files.newInputStream(Paths.get(path)))) {
			String xDescription = scanner.nextLine();
			String yDescription = scanner.nextLine();

			List<XYValue> values = extractValues(scanner.nextLine());

			int yMin = Integer.parseInt(scanner.nextLine());
			int yMax = Integer.parseInt(scanner.nextLine());

			int difference = Integer.parseInt(scanner.nextLine());

			barChart = new BarChart(values, xDescription, yDescription, yMin, yMax, difference);
		} catch (Exception e) {
			System.out.println("Invalid file given.");
			return null;
		}

		return barChart;
	}

	/**
	 * Extracting values form string to array of {@code XYValue}. Every value is
	 * separated with spaces.
	 * 
	 * @param line
	 *            line containing
	 * 
	 * @return List<XYValue> created from the given string
	 */
	private static List<XYValue> extractValues(String line) {
		List<XYValue> result = new ArrayList<>();

		String[] values = line.split("\\s+");

		for (String value : values) {
			String[] xAndY = value.split(",");

			result.add(new XYValue(Integer.parseInt(xAndY[0]), Integer.parseInt(xAndY[1])));
		}

		return result;
	}
}
