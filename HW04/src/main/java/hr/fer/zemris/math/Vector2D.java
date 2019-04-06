package hr.fer.zemris.math;

import java.util.Objects;

/**
 * Class {@code Vector2D} is a representation of two-dimensional vector
 * represented by two unit vectors 'i' and 'j' and their real coefficients 'x'
 * and 'y' (xi + yj).
 * <p>
 * A unit vector is a vector which has a magnitude of 1. There are two important
 * unit vectors which are commonly used and these are the vectors in the
 * direction of the x and y-axes. The unit vector in the direction of the x-axis
 * is 'i' and the unit vector in the direction of the y-axis is 'j'. Every
 * vector is a vector from the origin O = (0,0) to the point P = (x,y) .The
 * magnitude of a vector can be found using Pythagoras's theorem (square root of
 * sum of square of coefficients of i and j).
 * <p>
 * <p>
 * {@code Vector2D} offers methods for vector transformation such as
 * translation, rotation and scalar multiplication. {@code DELTA} defines
 * equality of the two vectors.
 * <p>
 * 
 * @author Filip Karacic
 *
 */
public class Vector2D {
	/**
	 * Coefficient of the unit vector i.
	 */
	private double x;
	/**
	 * Coefficient of the unit vector j.
	 */
	private double y;
	/**
	 * Defines the equality of the two vectors.
	 */
	private static final double DELTA = 1E-8;

	/**
	 * Constructs a two-dimensional vector {@code x}i + {@code y}j.
	 * 
	 * @param x
	 *            coefficient of the unit vector i
	 * @param y
	 *            coefficient of the unit vector j
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns coefficient of the unit vector i.
	 * 
	 * @return coefficient of the unit vector i
	 */
	public double getX() {
		return x;
	}

	/**
	 * Returns coefficient of the unit vector j.
	 * 
	 * @return coefficient of the unit vector j
	 */
	public double getY() {
		return y;
	}

	/**
	 * Translates this vector using the given vector.
	 * 
	 * @param offset
	 *            vector used for translation of this vector
	 * 
	 * @throws NullPointerException
	 *             if {@code offset} is {@code null}
	 */
	public void translate(Vector2D offset) {
		Objects.requireNonNull(offset);

		x += offset.x;
		y += offset.y;
	}

	/**
	 * Returns new {@code Vector2D} object created from translation of this vector
	 * with the given vector.
	 * 
	 * @param offset
	 *            vector used for translation of this vector
	 * @return new vector created from translation of this vector with the given
	 *         vector
	 * 
	 * @throws NullPointerException
	 *             if {@code offset} is {@code null}
	 */
	public Vector2D translated(Vector2D offset) {
		Objects.requireNonNull(offset);

		return new Vector2D(x + offset.x, y + offset.y);
	}

	/**
	 * Rotates this vector for the given angle. Angles are used as degrees.
	 * 
	 * @param angle
	 *            angle of rotation in degrees.
	 **/
	public void rotate(double angle) {
		double currentAngle = Math.atan2(y, x);

		angle *= Math.PI / 180;

		double magnitude = Math.sqrt(x * x + y * y);

		x = Math.cos(angle + currentAngle) * magnitude;
		y = Math.sin(angle + currentAngle) * magnitude;

	}

	/**
	 * Returns new {@code Vector2D} created from rotation of this vector for the
	 * given angle. Angles are used as degrees.
	 * 
	 * @param angle
	 *            angle of rotation in degrees.
	 * @return new vector created from rotation of this vector for the given angle
	 */
	public Vector2D rotated(double angle) {
		double currentAngle = Math.atan2(y, x);

		angle *= Math.PI / 180;

		double magnitude = Math.sqrt(x * x + y * y);

		return new Vector2D(Math.cos(angle + currentAngle) * magnitude, Math.sin(angle + currentAngle) * magnitude);
	}

	/**
	 * Multiplies this vector with the given scaler.
	 * 
	 * @param scaler scaler that multiplies this vector
	 */
	public void scale(double scaler) {
		x *= scaler;
		y *= scaler;
	}

	/**
	 * Returns new vector created from multiplying this vector with the given scaler.
	 * 
	 * @param scaler scaler that multiplies this vector to produce the new vector
	 *
	 * @return new vector created from multiplying this vector with the given scaler
	 */
	public Vector2D scaled(double scaler) {

		return new Vector2D(x * scaler, y * scaler);
	}

	/**
	 * Returns new {@code Vector2D} object representing a copy of this vector.
	 * 
	 * @return new vector as a copy of this vector
	 */
	public Vector2D copy() {
		return new Vector2D(x, y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		if (!(obj instanceof Vector2D))
			return false;

		Vector2D other = (Vector2D) obj;

		if (Math.abs(x - other.x) < DELTA && Math.abs(y - other.y) < DELTA)
			return true;

		return false;
	}

}