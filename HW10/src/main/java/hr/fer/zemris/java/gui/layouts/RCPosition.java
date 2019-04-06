package hr.fer.zemris.java.gui.layouts;

/**
 * Restrictions for the layout.
 * 
 * @author Filip Karacic
 *
 */
public class RCPosition {

	/**
	 * Constructor for the restrictions.
	 * 
	 * @param row
	 *            row number
	 * @param column
	 *            column number
	 * @throws IllegalArgumentException
	 *             if the given row or column number is less than zero
	 */
	public RCPosition(int row, int column) {
		if (row < 0 || column < 0)
			throw new IllegalArgumentException("Both row and column numbers must be non-negative integers. Were: row: "
					+ row + " column: " + column + ".");

		this.row = row;
		this.column = column;
	}

	/**
	 * Row number.
	 */
	private int row;
	/**
	 * Column number.
	 */
	private int column;

	/**
	 * Returns row number.
	 * 
	 * @return row number
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Returns column number.
	 * 
	 * @return column number
	 */
	public int getColumn() {
		return column;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + column;
		result = prime * result + row;
		
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		
		if (obj == null || !(obj instanceof RCPosition)) return false;
		
		RCPosition other = (RCPosition) obj;
		
		return column == other.column && row == other.row;
	}
	
	

}
