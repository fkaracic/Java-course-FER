package hr.fer.zemris.java.raytracer;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Ray casting is the use of ray–surface intersection tests to solve a variety
 * of problems in computer graphics and computational geometry. Ray casting is
 * the most basic of many computer graphics rendering algorithms that use the
 * geometric algorithm of ray tracing. Ray tracing-based rendering algorithms
 * operate in image order to render three-dimensional scenes to two-dimensional
 * images. Geometric rays are traced from the eye of the observer to sample the
 * light (radiance) traveling toward the observer from the ray direction.
 * <p>
 * 
 * Program uses parallelization for performances improvement.
 * 
 * @author Filip Karacic
 *
 */
public class RayCasterParallel {

	/**
	 * Used for comparison of doubles.
	 */
	private static final double DELTA = 1E-8;

	/**
	 * Maximal number of iterations.
	 */
	private static final int m = 16 * 16 * 16;

	/**
	 * Method called when program starts.
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);
	}

	/**
	 * Producer used to calculate data for drawing.
	 * 
	 * @return producer with calculation results for drawing.
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {

			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
					int width, int height, long requestNo, IRayTracerResultObserver observer) {

				System.out.println("Starting calculations...");

				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];

				Point3D eyeDirection = view.sub(eye).normalize();

				Point3D yAxis = viewUp.normalize()
						.sub(eyeDirection.scalarMultiply(eyeDirection.normalize().scalarProduct(viewUp))).normalize();
				Point3D xAxis = eyeDirection.vectorProduct(yAxis).normalize();

				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2.0))
						.add(yAxis.scalarMultiply(vertical / 2.0));

				Scene scene = RayTracerViewer.createPredefinedScene();

				ForkJoinPool pool = new ForkJoinPool();
				pool.invoke(new Calculation(0, height, width, height, horizontal, vertical, m, scene, eye, xAxis, yAxis,
						screenCorner, red, blue, green));
				pool.shutdown();

				System.out.println("Calculation over...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Informing over...");
			}
		};
	}

	/**
	 * Used for calculation of the data.
	 */
	private static class Calculation extends RecursiveAction {
		/**
		 * Serial version.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Minimal y.coordinate
		 */
		private int yMin;
		/**
		 * Maximal y-coordinate
		 */
		private int yMax;
		/**
		 * width of the image
		 */
		private int width;
		/**
		 * height of the image
		 * 
		 */
		private int height;
		/**
		 * horizontal line
		 * 
		 */
		private double horizontal;
		/**
		 * vertical line
		 * 
		 */
		private double vertical;
		/**
		 * maximal number of iterations
		 */
		private int m;
		/**
		 * Scene image-u.
		 */
		private Scene scene;
		/**
		 * Observers eye position
		 */
		private Point3D eye;
		/**
		 * vector representing x-axis.
		 */
		private Point3D xAxis;
		/**
		 * vector representing y-axis.
		 */
		private Point3D yAxis;
		/**
		 * Corner point of the screen.
		 */
		private Point3D screenCorner;
		/**
		 * Red color.
		 */
		private short[] red;
		/**
		 * Blue color.
		 */
		private short[] blue;
		/**
		 * Green Color
		 */
		private short[] green;

		/**
		 * threshold for this caster.
		 */
		private static final int TRESHOLD = 16;

		/**
		 * Creates new calculation class with provided arguments.
		 * 
		 * @param scene
		 *            scene of the image
		 * @param yMin
		 *            minimum Y-coordinate
		 * @param yMax
		 *            maximum Y-coordinate
		 * @param width
		 *            width of the calculation space.
		 * @param height
		 *            height of the calculation space.
		 * @param horizontal
		 *            horizontal width of the observed space.
		 * @param vertical
		 *            vertical width of the observed space.
		 * @param m
		 *            maximal number of iterations
		 * @param screenCorner
		 *            Screen point of the screen (upper left corner).
		 * @param xAxis
		 *            Vector of X-axis.
		 * @param yAxis
		 *            Vector of Y-axis
		 * @param eye
		 *            Spectators eye position.
		 * @param red
		 *            red color data.
		 * @param blue
		 *            blue color data.
		 * @param green
		 *            green color data.
		 */
		public Calculation(int yMin, int yMax, int width, int height, double horizontal, double vertical, int m,
				Scene scene, Point3D eye, Point3D xAxis, Point3D yAxis, Point3D screenCorner, short[] red, short[] blue,
				short[] green) {
			this.yMin = yMin;
			this.yMax = yMax;
			this.width = width;
			this.height = height;
			this.horizontal = horizontal;
			this.vertical = vertical;
			this.m = m;
			this.scene = scene;
			this.eye = eye;
			this.xAxis = xAxis;
			this.yAxis = yAxis;
			this.screenCorner = screenCorner;
			this.red = red;
			this.blue = blue;
			this.green = green;
		}

		@Override
		protected void compute() {
			if (yMax - yMin + 1 <= TRESHOLD) {
				computeDirect();
				return;
			}

			invokeAll(
					new Calculation(yMin, yMin + (yMax - yMin) / 2, width, height, horizontal, vertical, m, scene, eye,
							xAxis, yAxis, screenCorner, red, blue, green),
					new Calculation(yMin + (yMax - yMin) / 2, yMax, width, height, horizontal, vertical, m, scene, eye,
							xAxis, yAxis, screenCorner, red, blue, green));
		}

		/**
		 * Directly compute if the calculated space is not big enough.
		 */
		protected void computeDirect() {

			short[] rgb = new short[3];

			int offset = yMin * width;

			for (int y = yMin; y < yMax; y++) {
				for (int x = 0; x < width; x++) {
					Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(x / (width - 1.0) * horizontal))
							.sub(yAxis.scalarMultiply(y / (height - 1.0) * vertical));

					Ray ray = Ray.fromPoints(eye, screenPoint);

					tracer(scene, ray, rgb);

					red[offset] = rgb[0] > 255 ? 255 : rgb[0];
					green[offset] = rgb[1] > 255 ? 255 : rgb[1];
					blue[offset] = rgb[2] > 255 ? 255 : rgb[2];

					offset++;
				}
			}
		}
	}

	/**
	 * Traces image using ray-casting model and fills results into <code>rgb</code>
	 * array.
	 * 
	 * @param scene
	 *            Scene of the image.
	 * @param ray
	 *            Ray from the eye (viewer) to the screen point (origin).
	 * @param rgb
	 *            RGB color data.
	 */
	private static void tracer(Scene scene, Ray ray, short[] rgb) {
		double[] calculatedRgb = new double[3];

		calculatedRgb[0] = 15;
		calculatedRgb[1] = 15;
		calculatedRgb[2] = 15;

		RayIntersection closestIntersection = findClosestIntersection(scene, ray);

		if (closestIntersection != null) {
			determineColorFor(scene, ray, calculatedRgb, closestIntersection);
		}

		rgb[0] = (short) calculatedRgb[0];
		rgb[1] = (short) calculatedRgb[1];
		rgb[2] = (short) calculatedRgb[2];
	}

	/**
	 * Finds closest intersection between <code>ray</code> and any object in the
	 * scene.
	 * 
	 * @param scene
	 *            Scene of the image.
	 * @param ray
	 *            Ray used to find intersection.
	 * @return Closest intersection with any object in the scene, <code>null</code>
	 *         if it doesn't exist.
	 */
	private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
		RayIntersection closestIntersection = null;

		List<GraphicalObject> graphicalObjects = scene.getObjects();

		for (GraphicalObject graphicalObject : graphicalObjects) {
			RayIntersection currentIntersection = graphicalObject.findClosestRayIntersection(ray);

			if (currentIntersection != null) {
				if (closestIntersection == null
						|| closestIntersection.getDistance() > currentIntersection.getDistance()) {
					closestIntersection = currentIntersection;
				}
			}
		}

		return closestIntersection;
	}

	/**
	 * Determines color for specific intersection. Adds diffuse and reflective
	 * component if needed.
	 * 
	 * @param scene
	 *            Scene of the image.
	 * @param ray
	 *            Ray used to find intersection.
	 * @param calculatedRgb
	 *            RGB color data (which will be filled by results).
	 * @param intersection
	 *            Intersection between ray and some object (if it exists).
	 */
	private static void determineColorFor(Scene scene, Ray ray, double[] calculatedRgb, RayIntersection intersection) {
		List<LightSource> lightSources = scene.getLights();
		for (LightSource lightSource : lightSources) {
			Ray r = Ray.fromPoints(lightSource.getPoint(), intersection.getPoint());
			RayIntersection closestIntersection = findClosestIntersection(scene, r);

			if (closestIntersection != null) {
				double lsIntersectionDistance = lightSource.getPoint().sub(closestIntersection.getPoint()).norm();
				double eyeIntersectionDistance = lightSource.getPoint().sub(intersection.getPoint()).norm();

				if (Math.abs(lsIntersectionDistance - eyeIntersectionDistance) < DELTA) {
					addDiffusseComponent(lightSource, calculatedRgb, closestIntersection);
					addReflectiveComponent(lightSource, ray, calculatedRgb, closestIntersection);
				}
			}
		}
	}

	/**
	 * Adds diffuse component to RGB color data.
	 * 
	 * @param lightSource
	 *            light source to provide color data.
	 * @param calculatedRgb
	 *            RGB color data (which will be filled by results).
	 * @param intersection
	 *            intersection to focus on
	 */
	private static void addDiffusseComponent(LightSource lightSource, double[] calculatedRgb,
			RayIntersection intersection) {
		Point3D l = lightSource.getPoint().sub(intersection.getPoint()).normalize();
		Point3D n = intersection.getNormal();

		double max = Math.max(l.scalarProduct(n), 0.0);

		calculatedRgb[0] += lightSource.getR() * intersection.getKdr() * max;
		calculatedRgb[1] += lightSource.getG() * intersection.getKdg() * max;
		calculatedRgb[2] += lightSource.getB() * intersection.getKdb() * max;
	}

	/**
	 * Adds diffuse component to RGB color data.
	 * 
	 * @param lightSource
	 *            lightSource source to provide color data.
	 * @param ray
	 *            ray used to find intersection.
	 * @param calculatedRgb
	 *            RGB color data to be filled by results.
	 * @param intersection
	 *            intersection to focus on
	 */
	private static void addReflectiveComponent(LightSource lightSource, Ray ray, double[] calculatedRgb,
			RayIntersection intersection) {

		Point3D l = lightSource.getPoint().sub(intersection.getPoint()).normalize();
		Point3D n = intersection.getNormal().normalize();

		Point3D projection = n.scalarMultiply(l.scalarProduct(n));
		Point3D r = projection.scalarMultiply(2.0).sub(l);

		Point3D v = ray.start.sub(intersection.getPoint()).normalize();

		double cosine = r.normalize().scalarProduct(v.normalize());

		if (Double.compare(cosine, 0) >= 0) {
			cosine = Math.pow(cosine, intersection.getKrn());

			calculatedRgb[0] += lightSource.getR() * intersection.getKrr() * cosine;
			calculatedRgb[1] += lightSource.getG() * intersection.getKrg() * cosine;
			calculatedRgb[2] += lightSource.getB() * intersection.getKrb() * cosine;
		}
	}
}