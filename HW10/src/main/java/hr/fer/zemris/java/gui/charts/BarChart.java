package hr.fer.zemris.java.gui.charts;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents bar chart used for representation of some event, or just someone
 * inputs.
 * 
 * @author Filip Karacic
 *
 */
public class BarChart {
	/**
	 * List of the values.
	 */
	private List<XYValue> values;
	/**
	 * Description of the x-axis
	 */
	private String xDescription;
	/**
	 * Description of the y-axis
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
	 * Initializes newly created {@code BarChart} object representing bar chart info
	 * provider.
	 * 
	 * @param values
	 *            list of values
	 * @param xDescription
	 *            description of the x-axis
	 * @param yDescription
	 *            description of the y-axis
	 * @param yMin
	 *            minimal y value
	 * @param yMax
	 *            maximal y value
	 * @param difference
	 *            difference between two y values
	 * 
	 * @throws IllegalArgumentException
	 *             if the given difference is less than one
	 * @throws NullPointerException
	 *             if any of the given arguments is <code>null</code>
	 */
	public BarChart(List<XYValue> values, String xDescription, String yDescription, int yMin, int yMax,
			int difference) {
		this.values = new ArrayList<>(Objects.requireNonNull(values));
		this.xDescription = Objects.requireNonNull(xDescription);
		this.yDescription = Objects.requireNonNull(yDescription);

		this.yMin = yMin;
		this.yMax = yMax;

		if (difference < 1)
			throw new IllegalArgumentException("Difference between two y-values cannot be less or equals to zero.");

		this.difference = difference;
	}

	/**
	 * Returns list of values.
	 * 
	 * @return list of values
	 */
	public List<XYValue> getValues() {
		return values;
	}

	/**
	 * Returns description of the x-axis.
	 * 
	 * @return description of the x-axis
	 */
	public String getxDescription() {
		return xDescription;
	}

	/**
	 * Returns description of the y-axis.
	 * 
	 * @return description of the y-axis
	 */
	public String getyDescription() {
		return yDescription;
	}

	/**
	 * Returns minimal y value.
	 * 
	 * @return minimal y value
	 */
	public int getyMin() {
		return yMin;
	}

	/**
	 * Returns maximal y value.
	 * 
	 * @return maximal y value
	 */
	public int getyMax() {
		return yMax;
	}

	/**
	 * Returns the difference of the two y values 
	 * @return the difference of the two y values
	 */
	public int getDifference() {
		return difference;
	}

}
