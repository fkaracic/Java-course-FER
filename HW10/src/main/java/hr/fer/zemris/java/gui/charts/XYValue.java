package hr.fer.zemris.java.gui.charts;

/**
 * Represents values with x and y components.
 * 
 * @author Filip Karacic
 *
 */
public class XYValue {
	/**
	 * X value.
	 */
	private int x;
	/**
	 * Y value.
	 */
	private int y;
	
	/**
	 * Initializes newly created {@code XYValue} object.
	 * 
	 * @param x x value
	 * @param y y value
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/** 
	 * Returns x value.
	 * @return x value.
	 */
	public int getX() {
		return x;
	}
	
	/** 
	 * Returns y value.
	 * @return y value.
	 */
	public int getY() {
		return y;
	}
	
}
