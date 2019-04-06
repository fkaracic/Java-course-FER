package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.java.util.Util;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Program for calculation and drawing of Newton fractal. The Newton fractal is
 * a boundary set in the complex plane which is characterized by Newton's method
 * applied to a fixed polynomial or transcendental function.
 * <p>
 * 
 * Program allows user to enter two or more complex numbers in form 'a+bi'
 * representing roots of the complex polynomial. User can enter complex numbers
 * until 'done' is enter (case insensitive). If more than two complex numbers
 * are entered program calculates and draws Newton's fractal.
 * 
 * @author Filip Karacic
 *
 */
public class Newton {

	/**
	 * Max number of iteration
	 */
	private static final int m = 16 * 16 * 16;
	/**
	 * Threshold for Newton fractal calculation
	 **/
	private static final double THRESHOLD = 1E-3;

	/**
	 * Polynomial for Newton's fractal in form: (z-z0)*(z-z1)...(z-zn)
	 */
	private static ComplexRootedPolynomial rootedPolynom;
	/**
	 * Polynomial for Newton's fractal in form: zn*z^n+...+z1*z+z0
	 */
	private static ComplexPolynomial polynom;
	/**
	 * Derived polynomial.
	 */
	private static ComplexPolynomial derivedPolynom;

	/**
	 * Method called when program starts
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {

		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");

		List<Complex> roots = new ArrayList<>();

		try (Scanner scanner = new Scanner(System.in)) {
			int i = 0;
			while (true) {
				System.out.print("Root " + (i + 1) + "> ");
				String line = scanner.nextLine();

				if (line == null || line.equals("done"))
					break;

				Complex c = Util.stringToComplex(line);

				if (c == null) {
					System.out
							.println("Wrong complex number. Please enter complex number in form 'a+bi'. Was: " + line);
					continue;
				}

				roots.add(c);
				i++;
			}
		}

		if (roots.size() < 2) {
			System.out.println("At least two roots must be entered. Terminating program.");
			return;
		}

		System.out.println("Image of fractal will appear shortly. Thank you.");

		rootedPolynom = new ComplexRootedPolynomial(roots.toArray(new Complex[0]));
		polynom = rootedPolynom.toComplexPolynom();
		derivedPolynom = rootedPolynom.toComplexPolynom().derive();

		FractalViewer.show(new Producer());
	}

	/**
	 * Used for calculation of arguments for drawing fractal.
	 */
	private static class Calculation implements Callable<Void> {

		/**
		 * Minimal real part,
		 */
		double reMin;
		/**
		 * Maximal real part.
		 */
		double reMax;
		/**
		 * Minimal imaginary part.
		 */
		double imMin;
		/**
		 * Maximal imaginary part
		 */
		double imMax;
		/**
		 * Width of the image.
		 */
		int width;
		/**
		 * Height of the image.
		 */
		int height;
		/**
		 * Minimal y-coordinate.
		 */
		int yMin;
		/**
		 * Maximal y-coordinate
		 */
		int yMax;
		/**
		 * Maximal number of iterations
		 */
		int m;
		/**
		 * Array for calculation results.
		 */
		short[] data;

		/**
		 * Initializes newly created {@code Calculation} object representing calculation
		 * class with the given arguments.
		 * 
		 * @param reMin
		 *            minimum real part
		 * @param reMax
		 *            maximum real part
		 * @param imMin
		 *            minimum imaginary part
		 * @param imMax
		 *            maximum imaginary part
		 * @param width
		 *            width of the image
		 * @param height
		 *            height of the image
		 * @param yMin
		 *            minimum value of Y-coordinate
		 * @param yMax
		 *            maximum value of Y-coordinate
		 * @param m
		 *            maximal number of iterations
		 * @param data
		 *            array for calculation results
		 */
		public Calculation(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin,
				int yMax, int m, short[] data) {
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;

		}

		@Override
		public Void call() throws Exception {

			for (int y = yMin; y <= yMax; y++) {
				for (int x = 0; x < width; x++) {

					Complex zn = mapToComplexPlain(x, y, 0, width, 0, height, reMin, reMax, imMin, imMax);
					Complex zn1;

					int iterations = 0;
					double module;

					do {
						Complex numerator = rootedPolynom.apply(zn);
						Complex denominator = derivedPolynom.apply(zn);
						Complex fraction = numerator.divide(denominator);

						zn1 = zn.sub(fraction);
						module = zn1.sub(zn).module();
						zn = zn1;

						iterations++;
					} while ((iterations < m) && (module > THRESHOLD));

					short index = (short) rootedPolynom.indexOfClosestRootFor(zn1, THRESHOLD);
					data[x + y * width] = (short) (index == -1 ? 0 : index + 1);
				}
			}

			return null;
		}

		/**
		 * 
		 * Creates complex number from the given inputs.
		 * 
		 * @param x
		 *            x-coordinate
		 * @param y
		 *            y-coordinate
		 * @param xMin
		 *            minimal x-coordinate
		 * @param xMax
		 *            maximal x-coordinate
		 * @param yMin
		 *            minimal y-coordinate
		 * @param yMax
		 *            maximal y-coordinate
		 * @param reMin
		 *            minimal real part
		 * @param reMax
		 *            maximal real part
		 * @param imMin
		 *            minimal imaginary part
		 * @param imMax
		 *            maximal imaginary part
		 * 
		 * @return newly created complex number
		 */
		private Complex mapToComplexPlain(int x, int y, int xMin, int xMax, int yMin, int yMax, double reMin,
				double reMax, double imMin, double imMax) {
			double real = x * (reMax - reMin) / (xMax - 1.0) + reMin;
			double imaginary = (yMax - 1 - y) * (imMax - imMin) / (yMax - 1.0) + imMin;

			return new Complex(real, imaginary);
		}
	}

	/**
	 * Producer used for calculating and drawing fractal. Producer uses
	 * parallelization in order to improve performances of the calculating and
	 * drawing.
	 */
	private static class Producer implements IFractalProducer {
		/**
		 * An Executor that provides methods to manage termination and methods that can
		 * produce a Future for tracking progress of one or more asynchronous tasks.
		 */
		private ExecutorService executor;

		/**
		 * Constructor for this producer.
		 */
		public Producer() {
			executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), new ThreadFactory() {

				@Override
				public Thread newThread(Runnable r) {
					Thread thr = new Thread(r);

					thr.setDaemon(true);

					return thr;
				}
			});
		}

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer) {

			short[] data = new short[width * height];
			final int tapes = 8 * Runtime.getRuntime().availableProcessors();
			int yPerTape = height / tapes;

			List<Future<Void>> results = new ArrayList<Future<Void>>();

			for (int i = 0; i < tapes; i++) {
				int yMin = i * yPerTape;
				int yMax = (i + 1) * yPerTape - 1;

				if (i == (tapes - 1)) {
					yMax = height - 1;
				}

				Calculation job = new Calculation(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data);
				results.add(executor.submit(job));
			}

			for (Future<Void> job : results) {
				try {
					job.get();
				} catch (InterruptedException | ExecutionException ignorable) {
				}
			}

			observer.acceptResult(data, (short) (polynom.order() + 1), requestNo);

		}
	}
}