package hr.fer.zemris.lsystems.impl;

import java.awt.Color;
import java.util.Objects;

import hr.fer.zemris.math.Vector2D;

/**
 * {@code TurtleState} class represents state of the turtle. State is defined by
 * the two-dimensional vector of current position, two-dimensional unit vector
 * of the current direction, color of the drawing line and effective step
 * length.
 * 
 * @author Filip Karacic
 *
 */
public class TurtleState {
	/**
	 * Two dimensional vector of the current position of this turtle.
	 */
	private Vector2D position;
	/**
	 * Two dimensional unit vector of the direction of this turtle.
	 */
	private Vector2D direction;
	/**
	 * Color of the line of this turtle.
	 */
	private Color color;
	/**
	 * Effective step length of this turtle.
	 */
	private double stepLength;

	/**
	 * Initializes newly created {@code TurtleState} object representing state of
	 * turtle defined by vectors of position and direction, color and effective step
	 * length.
	 * 
	 * @param position
	 *            vector of current position of this turtle
	 * @param direction
	 *            unit vector of current dirrection of this turtle
	 * @param color
	 *            color of the line of this turtle
	 * @param stepLength
	 *            effective step length of this turtle
	 * @throws NullPointerException if the given position, direction or color is {@code null}
	 */
	public TurtleState(Vector2D position, Vector2D direction, Color color, double stepLength) {
		this.position = Objects.requireNonNull(position);
		this.direction = Objects.requireNonNull(direction);
		this.color = Objects.requireNonNull(color);
		this.stepLength = stepLength;
	}

	/**
	 * Returns new {@code TurtleState} object representing a copy of this state.
	 * 
	 * @return new {@code TurtleState} object representing a copy of this state.
	 */
	public TurtleState copy() {
		return new TurtleState(position.copy(), direction.copy(), color, stepLength);
	}

	/**
	 * Returns current position of this state.
	 * 
	 * @return current position of this state
	 */
	public Vector2D getPosition() {
		return position;
	}

	/**
	 * Sets the position of this turtle on the given position.
	 * 
	 * @param position
	 *            new position on which this turtle's position is set
	 * @throws NullPointerException
	 *             if {@code position} is {@code null}
	 */
	public void setPosition(Vector2D position) {
		this.position = Objects.requireNonNull(position);
	}

	/**
	 * Returns the unit vector of the current direction of this turtle.
	 * 
	 * @return the unit vector of the current direction of this turtle
	 */
	public Vector2D getDirection() {
		return direction;
	}

	/**
	 * Sets the direction of this turtle on the given position.
	 * 
	 * @param direction
	 *            new direction on which this turtle's direction is set
	 * @throws NullPointerException
	 *             if {@code direction} is {@code null}
	 */
	public void setDirection(Vector2D direction) {
		this.direction = Objects.requireNonNull(direction);
	}

	/**
	 * Returns the color of this turtle.
	 * 
	 * @return color of this turtle.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Sets the color for this turtle on the given color.
	 * 
	 * @param color
	 *            new color on which color for this turtle is set
	 * @throws NullPointerException
	 *             if {@code color} is {@code null}
	 */
	public void setColor(Color color) {
		this.color = Objects.requireNonNull(color);
	}

	/**
	 * Returns effective length of the step for this turtle.
	 * 
	 * @return effective length of the step for this turtle
	 */
	public double getStepLength() {
		return stepLength;
	}

	/**
	 * Sets the effective length of the step for this turtle.
	 * 
	 * @param stepLength
	 *            new effective length on which effective length for this turtle is
	 *            set
	 */
	public void setStepLength(double stepLength) {
		this.stepLength = stepLength;
	}

}
