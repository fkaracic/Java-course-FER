package hr.fer.zemris.java.raytracer.model;

import java.util.Objects;

/**
 * {@code Sphere} representing a sphere that is a graphical object.
 * 
 * @author Filip Karacic
 *
 */
public class Sphere extends GraphicalObject {

	/**
	 * Center of the sphere.
	 */
	private Point3D center;
	/**
	 * Radius of the sphere.
	 */
	private double radius;
	/**
	 * Diffuse component coefficient for red color.
	 */
	private double kdr;
	/**
	 * Diffuse component coefficient for green color
	 */
	private double kdg;
	/**
	 * Diffuse component coefficient for blue color
	 */
	private double kdb;
	/**
	 * Reflective component coefficient for red color.
	 */
	private double krr;
	/**
	 * Reflective component coefficient for green color.
	 */
	private double krg;
	/**
	 * Reflective component coefficient for blue color.
	 */
	private double krb;
	/**
	 * Exponent to power cosine(n) in Phong's model.
	 */
	private double krn;

	/**
	 * Initializes newly created {@code Sphere} object with the given arguments.
	 * 
	 * @param center
	 *            Center of this sphere.
	 * @param radius
	 *            Radius of this sphere.
	 * @param kdr
	 *            Diffuse component coefficient for red color.
	 * @param kdg
	 *            Diffuse component coefficient for green color.
	 * @param kdb
	 *            Diffuse component coefficient for blue color.
	 * @param krr
	 *            Reflective component coefficient for red color.
	 * @param krg
	 *            Reflective component coefficient for green color.
	 * @param krb
	 *            Reflective component coefficient for blue color.
	 * @param krn
	 *            Exponent to power cosine(n) in Phong's model.
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		this.center = Objects.requireNonNull(center, "Center of the sphere must not be null.");

		if (radius <= 0)
			throw new IllegalArgumentException("Radius of the sphere must not be equal or less than zero");

		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {

		double a = 1.0; // rayDirection.scalarProduct(rayDirection) == 1
		double b = 2 * ray.direction.scalarProduct(ray.start.sub(center));

		Point3D SC = ray.start.sub(center);
		double c = SC.scalarProduct(SC) - radius * radius;

		double D = b * b - 4 * a * c;

		if (D < 0)
			return null;

		double x1 = (-b + Math.sqrt(D)) / (2 * a);
		double x2 = (-b - Math.sqrt(D)) / (2 * a);

		if (x1 < 0 && x2 < 0)
			return null;

		Point3D p1 = ray.start.add(ray.direction.scalarMultiply(x1));
		Point3D p2 = ray.start.add(ray.direction.scalarMultiply(x2));

		double p1S = Math.abs(x1);
		double p2S = Math.abs(x2);

		int comparison = Double.compare(p1S, p2S);

		Point3D closerPoint = comparison <= 0 ? p1 : p2;
		double closerDistance = comparison <= 0 ? p1S : p2S;
		boolean outer = closerPoint.sub(center).norm() > radius ? false : true;

		return new RayIntersection(closerPoint, closerDistance, outer) {

			@Override
			public Point3D getNormal() {
				return getPoint().sub(center).normalize();
			}

			@Override
			public double getKrr() {
				return krr;
			}

			@Override
			public double getKrn() {
				return krn;
			}

			@Override
			public double getKrg() {
				return krg;
			}

			@Override
			public double getKrb() {
				return krb;
			}

			@Override
			public double getKdr() {
				return kdr;
			}

			@Override
			public double getKdg() {
				return kdg;
			}

			@Override
			public double getKdb() {
				return kdb;
			}
		};
	}
}