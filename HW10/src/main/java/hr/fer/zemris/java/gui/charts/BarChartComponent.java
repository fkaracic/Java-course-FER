package hr.fer.zemris.java.gui.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JComponent;

/**
 * Component for data drawing. Used for drawing bar charts that represents some
 * data.
 * 
 * @author Filip Karacic
 *
 */
public class BarChartComponent extends JComponent {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Inset from the left.
	 */
	private static final int LEFT_INSET = 75;

	/**
	 * Inset from the bottom.
	 */
	private static final int BOTTOM_INSET = 50;

	/**
	 * Lines color.
	 */
	private static final Color LINES = new Color(153, 153, 153);

	/**
	 * Bar color.
	 */
	private static final Color BAR = new Color(255, 100, 50);

	/**
	 * Chart containing info.
	 */
	private BarChart chart;

	/**
	 * List of the values.
	 */
	private List<XYValue> values;
	/**
	 * Description of the x-axis.
	 */
	private String xDescription;
	/**
	 * Description of the y-axis.
	 */
	private String yDescription;

	/**
	 * Minimal value for y.
	 */
	private int yMin;

	/**
	 * Maximum value for y.
	 */
	private int yMax;
	/**
	 * Difference between two y values.
	 */
	private int difference;

	/**
	 * Constructor for {@code BarChartComponent}.
	 * 
	 * @param barChart
	 *            bar chart info provider
	 */
	public BarChartComponent(BarChart barChart) {
		this.chart = Objects.requireNonNull(barChart);

		initializeChartInfo();
	}

	/**
	 * Initializes info for chart bar.
	 */
	private void initializeChartInfo() {
		xDescription = chart.getxDescription();
		yDescription = chart.getyDescription();

		values = new ArrayList<>(chart.getValues());
		values.sort((c1, c2) -> Integer.compare(c1.getX(), c2.getX()));

		yMin = chart.getyMin();
		yMax = chart.getyMax();
		difference = chart.getDifference();
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;

		Insets insets = getInsets();
		int additionalInset = 10;

		Dimension dimensions = getSize();

		int width = dimensions.width - (insets.left + insets.right) - 2 * additionalInset;
		int height = dimensions.height - (insets.bottom + insets.top) - 2 * additionalInset;

		int rows = (yMax - yMin) / difference;
		int columns = values.size();

		drawings(g2D, width, height, rows, columns, additionalInset, dimensions);
	}

	/**
	 * Draws bar chart to this component.
	 * 
	 * @param g2D
	 *            graphics context
	 * @param width
	 *            width available
	 * @param height
	 *            height available
	 * @param rows
	 *            rows available
	 * @param columns
	 *            columns available
	 * @param additionalInset
	 *            additional inset
	 * 
	 * @param dimensions
	 *            dimension of the window
	 */
	private void drawings(Graphics2D g2D, int width, int height, int rows, int columns, int additionalInset,
			Dimension dimensions) {

		g2D.setColor(Color.WHITE);
		g2D.fillRect(0, 0, dimensions.width, dimensions.height);

		int barWidth = (width - LEFT_INSET - additionalInset) / columns;
		int barHeight = (height - BOTTOM_INSET - additionalInset) / rows;
		int lineOverflow = 6;

		g2D.setFont(g2D.getFont().deriveFont(Font.BOLD).deriveFont(14f));
		drawVerticalLines(g2D, barHeight, barWidth, rows, columns, height, lineOverflow);
		drawHorizontalLines(g2D, barHeight, barWidth, rows, columns, height, lineOverflow);

		int xSize = calculateSize(LEFT_INSET - lineOverflow, width + additionalInset, height - BOTTOM_INSET,
				height - BOTTOM_INSET);
		int ySize = calculateSize(LEFT_INSET, LEFT_INSET, height - BOTTOM_INSET + lineOverflow, 0);

		drawXAxis(g2D, LEFT_INSET - lineOverflow, height - BOTTOM_INSET, xSize);
		drawYAxis(g2D, LEFT_INSET, height - BOTTOM_INSET + lineOverflow, ySize);

		drawBar(g2D, columns, height, barHeight, barWidth, lineOverflow);
	}

	/**
	 * Draws vertical lines for this chart to this component.
	 * 
	 * @param g2D
	 *            graphics context
	 * @param barHeight
	 *            height of the bar
	 * @param barWidth
	 *            width of the bar
	 * @param height
	 *            height available
	 * @param rows
	 *            rows available
	 * @param columns
	 *            columns available
	 * @param lineOverflow
	 *            line's overflow
	 * 
	 **/
	private void drawVerticalLines(Graphics2D g2D, int barHeight, int barWidth, int rows, int columns, int height,
			int lineOverflow) {
		int max = height - BOTTOM_INSET - rows * barHeight;

		int j = LEFT_INSET;

		g2D.drawLine(j, height - BOTTOM_INSET, j, height - BOTTOM_INSET + lineOverflow);

		g2D.setColor(Color.GRAY.darker());

		g2D.setStroke(new BasicStroke(2));
		g2D.drawLine(j, max - lineOverflow, j, height - BOTTOM_INSET);

		g2D.setColor(Color.BLACK);

		g2D.setStroke(new BasicStroke(0));
		for (int i = 0; i < columns; i++) {
			g2D.setColor(Color.PINK.darker());
			g2D.drawLine(j, max - lineOverflow, j, height - BOTTOM_INSET);

			g2D.setColor(Color.BLACK);
			int num = values.get(i).getX();
			int length = Integer.valueOf(num).toString().length();
			g2D.drawString(num + "", j + (barWidth / 2) - length / 2, height - 2 * (BOTTOM_INSET / 3));

			j += barWidth;
		}

		g2D.setColor(Color.PINK.darker());
		g2D.drawLine(j, max - lineOverflow, j, height - BOTTOM_INSET);

		writeXDescription(g2D, columns, barWidth, height);

	}

	/**
	 * Draws horizontal lines of the chart to this component.
	 * 
	 * @param g2D
	 *            graphics context
	 * @param barHeight
	 *            height of the bar
	 * @param barWidth
	 *            width of the bar
	 * @param height
	 *            height available
	 * @param rows
	 *            rows available
	 * @param columns
	 *            columns available
	 * @param lineOverflow
	 *            line's overflow
	 * 
	 **/
	private void drawHorizontalLines(Graphics2D g2D, int barHeight, int barWidth, int rows, int columns, int height,
			int lineOverflow) {

		int max = columns * barWidth + LEFT_INSET;

		g2D.setColor(Color.pink.darker());
		g2D.setStroke(new BasicStroke(2));
		g2D.drawLine(LEFT_INSET, height - BOTTOM_INSET, max + lineOverflow, height - BOTTOM_INSET);
		g2D.setStroke(new BasicStroke(0));

		int k = 0;
		for (int i = 0, j = height - BOTTOM_INSET; i <= rows; i++, j -= barHeight) {
			g2D.setColor(Color.GRAY);
			g2D.drawLine(LEFT_INSET - lineOverflow, j, LEFT_INSET, j);

			g2D.setColor(Color.pink.darker());
			g2D.drawLine(LEFT_INSET, j, max + lineOverflow, j);

			g2D.setColor(Color.BLACK);

			String yScale = yMin + k * difference + "";
			int centerAllignment = 5;
			g2D.drawString(yScale, (int) (LEFT_INSET / 1.1) - lineOverflow - g2D.getFontMetrics().stringWidth(yScale),
					j + centerAllignment);
			k++;
		}

		writeYDescription(g2D, height);
	}

	/**
	 * Writes description of the x-axis
	 * 
	 * @param g2D
	 *            {@link Graphics} context
	 * @param columns
	 *            number columns
	 * @param barWidth
	 *            width of the bar
	 * @param height
	 *            available height
	 */
	private void writeXDescription(Graphics2D g2D, int columns, int barWidth, int height) {
		g2D.setColor(Color.BLACK);

		Font defaultFont = g2D.getFont();

		g2D.setFont(g2D.getFont().deriveFont(13f).deriveFont(Font.PLAIN));

		g2D.drawString(xDescription, columns * barWidth / 2, height - (BOTTOM_INSET / 5));

		g2D.setFont(defaultFont);
	}

	/**
	 * Writes description for the y-axis
	 * 
	 * @param g2D
	 *            graphic context
	 * @param height
	 *            available height
	 */
	private void writeYDescription(Graphics2D g2D, int height) {
		AffineTransform defaultAt = g2D.getTransform();
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);

		g2D.setTransform(at);

		Font defaultFont = g2D.getFont();
		g2D.setFont(g2D.getFont().deriveFont(17f).deriveFont(Font.PLAIN));
		g2D.drawString(yDescription, (int) (-height / 1.5), LEFT_INSET / 3);

		g2D.setTransform(defaultAt);

		g2D.setFont(defaultFont);
	}

	/**
	 * Draws bar into the component
	 * 
	 * @param g2D
	 *            graphic context
	 * @param columns
	 *            available columns
	 * @param height
	 *            available height
	 * @param barHeight
	 *            available bar height
	 * @param barWidth
	 *            available bar width
	 * @param lineOverflow
	 *            line overflow
	 */
	private void drawBar(Graphics2D g2D, int columns, int height, int barHeight, int barWidth, int lineOverflow) {
		for (int i = 0, j = LEFT_INSET; i < columns; i++, j += barWidth) {
			int barSize = calculateBarSize(values.get(i).getY());

			int heightBar = (barSize - yMin) * barHeight / difference;
			g2D.setColor(BAR);
			g2D.fillRect(j, height - BOTTOM_INSET - heightBar, barWidth, heightBar);

			g2D.setColor(Color.WHITE);
			g2D.drawLine(j + barWidth - 1, height - BOTTOM_INSET - heightBar, j + barWidth - 1, height - BOTTOM_INSET);

			g2D.setColor(Color.GRAY);
			g2D.drawLine(j + barWidth - 1, height - BOTTOM_INSET, j + barWidth - 1,
					height - BOTTOM_INSET + lineOverflow);

		}
	}

	/**
	 * Calculates size of the bar.
	 * 
	 * @param value
	 *            current value
	 * @return Calculates size of the bar.
	 */
	private int calculateBarSize(int value) {
		return value < yMin ? yMin : value > yMax ? yMax : value;
	}

	/**
	 * Draws x-axis.
	 * 
	 * @param g2D
	 *            graphic context
	 * @param x
	 *            x-component of the first value
	 * @param y
	 *            y-component of the first value
	 * @param xSize
	 *            length of the line
	 */
	private void drawXAxis(Graphics2D g2D, int x, int y, int xSize) {

		AffineTransform defaultTransform = g2D.getTransform();

		AffineTransform at = AffineTransform.getTranslateInstance(x, y);
		at.rotate(0.0);
		g2D.transform(at);

		g2D.setColor(LINES);
		g2D.setStroke(new BasicStroke(2));
		g2D.drawLine(0, 0, xSize - 10, 0);

		drawAxisEnd(xSize - 6, g2D);

		g2D.setTransform(defaultTransform);
	}

	/**
	 * Draws y-axis.
	 * 
	 * @param g2D
	 *            graphics context
	 * @param x
	 *            x-component of the first value
	 * @param y
	 *            y-component of the first value
	 * @param ySize
	 *            length of the line
	 */
	private void drawYAxis(Graphics2D g2D, int x, int y, int ySize) {

		AffineTransform defaultTransform = g2D.getTransform();

		AffineTransform at = AffineTransform.getTranslateInstance(x, y);
		at.rotate(-Math.PI / 2);
		g2D.transform(at);

		g2D.setColor(LINES);
		g2D.setStroke(new BasicStroke(2));
		g2D.drawLine(0, 0, ySize - 4, 0);

		drawAxisEnd(ySize - 2, g2D);

		g2D.setTransform(defaultTransform);
	}

	/**
	 * Calculates distance between the two given points.
	 * 
	 * @param x0
	 *            x-component of the first value
	 * @param y0
	 *            y-component of the first value
	 * @param x1
	 *            x-component of the second value
	 * @param y1
	 *            y-component of the first value
	 * 
	 * @return result of distance calculation between the points
	 */
	private int calculateSize(int x0, int x1, int y0, int y1) {
		int x = x1 - x0;
		int y = y1 - y0;

		return (int) Math.sqrt(x * x + y * y);
	}

	/**
	 * Draws end of the axis(i.e. triangle).
	 * 
	 * @param size
	 *            size for the calculation
	 * @param g2D
	 *            graphic context
	 */
	private void drawAxisEnd(int size, Graphics2D g2D) {
		int triangleSize = 5;
		g2D.fillPolygon(new int[] { size, size - triangleSize, size - triangleSize, size },
				new int[] { 0, -triangleSize, triangleSize, 0 }, 3);

	}

}