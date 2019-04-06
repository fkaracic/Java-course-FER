package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Function;

/**
 * Layout manager for the calculator. It has 5 rows and 7 columns and can only
 * have 31 components added and only one component is allowed per position.
 * 
 * @author Filip Karacic
 *
 */
public class CalcLayout implements LayoutManager2 {
	/**
	 * Distance between each row and each column.
	 */
	private int distance;
	/**
	 * Default distance for each row and each column.
	 */
	private static final int DEFAULT_DISTANCE = 0;

	/**
	 * Default alignment for x.
	 */
	private static final float DEFAULT_ALINGMENT_X = 0.5f;

	/**
	 * Default alignment for y.
	 */
	private static final float DEFAULT_ALINGMENT_Y = 0.5f;

	/**
	 * Number of rows;
	 */
	private static final int ROWS = 5;

	/**
	 * Number of columns.
	 */
	private static final int COLUMNS = 7;

	/**
	 * Map of components and their positions.
	 */
	private Map<Component, RCPosition> elements;

	/**
	 * Constructor that sets distance between each row and each column to the
	 * default value: zero.
	 */
	public CalcLayout() {
		this(DEFAULT_DISTANCE);
	}

	/**
	 * Constructor that sets distance between each row and each column to the given
	 * value.
	 * 
	 * @param distance
	 *            distance of each row and each column
	 * 
	 * @throws CalcLayoutException
	 *             if the given distance is less than zero
	 */
	public CalcLayout(int distance) {
		if (distance < 0)
			throw new CalcLayoutException(
					"Distance between rows and columns cannot be less than zero. Was: " + distance);

		this.distance = distance;
		elements = new HashMap<>();
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
	}

	@Override
	public void layoutContainer(Container target) {
		Insets insets = Objects.requireNonNull(target).getInsets();

		int x = insets.left, y = insets.top;

		Dimension preferredComponentSize = calculateSize(target, Component::getPreferredSize, true);
		Dimension preferredLayoutSize = preferredLayoutSize(target);

		int width = (int) (preferredComponentSize.width * target.getWidth() / preferredLayoutSize.width);
		int height = (int) (preferredComponentSize.height * target.getHeight() / preferredLayoutSize.height);

		for (Entry<Component, RCPosition> entry : elements.entrySet()) {
			Component comp = entry.getKey();
			RCPosition position = entry.getValue();

			if (position.getRow() == 1 && position.getColumn() == 1) {
				comp.setBounds(x, y, width * 5 + 4 * distance, height);
			} else {
				comp.setBounds(x + (position.getColumn() - 1) * (width + distance),
						y + (position.getRow() - 1) * (height + distance), width, height);
			}
		}

	}

	@Override
	public Dimension minimumLayoutSize(Container target) {
		return calculateSize(target, comp -> comp.getMinimumSize(), false);
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return calculateSize(target, comp -> comp.getMaximumSize(), false);
	}

	@Override
	public Dimension preferredLayoutSize(Container target) {
		return calculateSize(target, comp -> comp.getPreferredSize(), false);
	}

	/**
	 * Calculates size of the given container for the given function.
	 * 
	 * @param target
	 *            container
	 * @param function
	 *            function for size calculation
	 * @param component
	 *            {@code true} if component's dimensions needs to be calculated
	 * 
	 * @return calculated size
	 */
	private Dimension calculateSize(Container target, Function<Component, Dimension> function, boolean component) {
		int width = 0;
		int height = 0;

		Insets insets = target.getInsets();

		for (Entry<Component, RCPosition> entry : elements.entrySet()) {
			Dimension current = function.apply(entry.getKey());

			if (current != null) {
				if(entry.getValue().equals(new RCPosition(1, 1))) {
					height = height < current.getHeight() ? (int) current.getHeight() : height;
					int widthOfOne = (current.width - 4*distance) / 5;
					width = widthOfOne <= width ? width : widthOfOne;
					continue;
				}
				
				width = width < current.getWidth() ? (int) current.getWidth() : width;
				height = height < current.getHeight() ? (int) current.getHeight() : height;
			}
		}

		if (component) {
			return new Dimension(width, height);
		}

		return new Dimension(insets.left + insets.right + distance * (COLUMNS - 1) + width * COLUMNS,
				insets.top + insets.bottom + distance * (ROWS - 1) + height * ROWS);
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		elements.remove(Objects.requireNonNull(comp));
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		Objects.requireNonNull(comp);
		Objects.requireNonNull(constraints);

		RCPosition position = getPosition(constraints);

		elements.put(comp, position);
	}

	/**
	 * Returns position of the component given as the constraint.
	 * 
	 * @param constraints
	 *            constraint for the layout
	 * 
	 * @return position of the component given as the constraint.
	 */
	private RCPosition getPosition(Object constraints) {
		RCPosition position;

		if (constraints instanceof String) {
			position = positionFromString((String) constraints);
		}

		else if (constraints instanceof RCPosition) {
			position = (RCPosition) constraints;
		}

		else {
			throw new CalcLayoutException(
					"Invalid constraint given. Must be of type: RCPosition. Was: " + constraints.getClass().getName());
		}

		if (!validPosition(position))
			throw new CalcLayoutException(
					"Rows must be in [1,5] and columns in [1,7]. Additionaly, if row = 1 then column can be only 1,6 or 7. Were: row: "
							+ position.getRow() + "column: " + position.getColumn());

		if (elements.containsValue(position)) {
			throw new CalcLayoutException("Cannot add more than one component to the single position.");
		}

		return position;
	}

	/**
	 * Returns position of the component string given as the constraint.
	 * 
	 * @param constraints
	 *            constraint for the layout
	 * 
	 * @return position of the component given as the constraint.
	 */
	private RCPosition positionFromString(String constraints) {
		if (!constraints.matches("\\d+,\\d+")) {
			throw new CalcLayoutException("Invalid string given as constraint. Was: " + constraints);
		}

		String[] con = constraints.split(",");

		try {
			return new RCPosition(Integer.parseInt(con[0]), Integer.parseInt(con[1]));
		} catch (NumberFormatException e) {
			throw new CalcLayoutException("Both row and column number must be positive integers.");
		}
	}

	/**
	 * Checks position given. Returns <code>true</code> if valid.
	 * 
	 * @param position
	 *            position of the given continet
	 * 
	 * @return <code>true</code> if valid
	 */
	private boolean validPosition(RCPosition position) {
		int row = position.getRow();
		int column = position.getColumn();

		if (row < 1 || row > 5 || column < 1 || column > 7)
			return false;

		return row != 1 || !(column > 1 && column < 6);
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return DEFAULT_ALINGMENT_X;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return DEFAULT_ALINGMENT_Y;
	}

	@Override
	public void invalidateLayout(Container target) {
	}

}
