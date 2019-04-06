package hr.fer.zemris.math;

import java.util.Objects;

/**
 * Representing vector in three dimensions. In three-dimensional space, there is
 * a standard Cartesian coordinate system (x,y,z). Starting with a point which
 * we call the origin, construct three mutually perpendicular axes, which we
 * call the x-axis, the y-axis, and the z-axis.
 * <p>
 * 
 * Just as in two dimensions, it is also possible to denote three-dimensional
 * vectors in terms of the standard unit vectors, i, j, and k. These vectors are
 * the unit vectors in the positive x, y, and z direction, respectively. In
 * terms of coordinates, we can write them as i=(1,0,0), j=(0,1,0), and
 * k=(0,0,1). We can express any three-dimensional vector as a sum of scalar
 * multiples of these unit vectors in the form v=(a, b, c)=a*i+b*j+c*k.
 * 
 * @author Filip Karacic
 *
 */
public class Vector3 {
	/**
	 * The first coordinate of this vector.
	 */
	double x;
	/**
	 * The second coordinate of this vector.
	 */
	double y;
	/**
	 * The third coordinate of this vector.
	 */
	double z;

	/**
	 * Initializes newly created {@code Vector3} object representing
	 * three-dimensional vector with the given end coordinates.
	 * 
	 * @param x
	 *            the first coordinate
	 * @param y
	 *            the second coordinate
	 * @param z
	 *            the third coordinate
	 */
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Return the norm of this vector.
	 * 
	 * @return the norm of this vector
	 */
	public double norm() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * Returns new vector representing this vector that has been normalized (has
	 * norm equal to one).
	 * 
	 * @return new vector representing this vector that has been normalized
	 */
	public Vector3 normalized() {
		double norm = norm();

		if (norm == 0)
			return new Vector3(0.0, 0.0, 0.0);

		return new Vector3(x / norm, y / norm, z / norm);
	}

	/**
	 * Returns new three-dimensional vector calculated from addition of this vector
	 * and the other given vector.
	 * 
	 * @param other
	 *            the other vector
	 * 
	 * @return result of the addition of this and other vector
	 * 
	 * @throws NullPointerException
	 *             if the {@code other} is <code>null</code>
	 */
	public Vector3 add(Vector3 other) {
		Objects.requireNonNull(other);

		return new Vector3(this.x + other.x, this.y + other.y, this.z + other.z);
	}

	/**
	 * Returns new three-dimensional vector calculated from subtraction of this
	 * vector and the other given vector.
	 * 
	 * @param other
	 *            the other vector
	 * 
	 * @return result of the subtraction of this and other vector
	 * 
	 * @throws NullPointerException
	 *             if the {@code other} is <code>null</code>
	 */
	public Vector3 sub(Vector3 other) {
		Objects.requireNonNull(other);

		return new Vector3(this.x - other.x, this.y - other.y, this.z - other.z);
	}

	/**
	 * Calculates scalar product of this vector and the other given.
	 * 
	 * @param other
	 *            other complex number
	 * 
	 * @return scalar product of the two given vectors
	 * 
	 * @throws NullPointerException
	 *             if the {@code other} is <code>null</code>
	 */
	public double dot(Vector3 other) {
		Objects.requireNonNull(other);

		return this.x * other.x + this.y * other.y + this.z * other.z;
	}

	/**
	 * Calculates cross(vector) product of this vector and the other given.
	 * 
	 * @param other
	 *            other complex number
	 * 
	 * @return cross(vector) product of the two given vectors
	 * 
	 * @throws NullPointerException
	 *             if the {@code other} is <code>null</code>
	 */
	public Vector3 cross(Vector3 other) {
		Objects.requireNonNull(other);

		return new Vector3(this.y * other.z - this.z * other.y, this.z * other.x - this.x * other.z,
				this.x * other.y - this.y * other.x);
	}

	/**
	 * Calculates new vector by scaling this vector with the given scalar.
	 * 
	 * @param s
	 *            scalar
	 * 
	 * @return this vector but scaled with the given scalar
	 */
	public Vector3 scale(double s) {
		return new Vector3(s * x, s * y, s * z);
	}

	/**
	 * Calculates cosines of the angle between this and the given vector.
	 * 
	 * @param other
	 *            other complex number
	 * 
	 * @return cosines of the angle between this and the given vector
	 * 
	 * @throws NullPointerException
	 *             if the {@code other} is <code>null</code>
	 */
	public double cosAngle(Vector3 other) {
		Objects.requireNonNull(other);

		return this.dot(other) / (this.norm() * other.norm());
	}

	/**
	 * Returns the first coordinate
	 * 
	 * @return the first coordinate
	 */
	public double getX() {
		return x;
	}

	/**
	 * Returns the second coordinate
	 * 
	 * @return the second coordinate
	 */
	public double getY() {
		return y;
	}

	/**
	 * Returns the third coordinate
	 * 
	 * @return the third coordinate
	 */
	public double getZ() {
		return z;
	}

	/**
	 * Returns this vector's coordinates as an array.
	 * 
	 * @return this vector's coordinates as an array
	 */
	public double[] toArray() {
		return new double[] { x, y, z };
	}
	
	@Override
	public String toString() {
		return String.format("(%.6f, %.6f, %.6f)", x, y, z);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(z);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		
		if (!(obj instanceof Vector3)) return false;
		
		Vector3 other = (Vector3) obj;
		
		return Double.compare(this.x, other.x) == 0 && Double.compare(this.y, other.y) == 0 && Double.compare(this.z, other.z) == 0;
	}
	
	

}
