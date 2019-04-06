package hr.fer.zemris.java.hw06.demo2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Represents collection of prime numbers. A prime number (or a prime) is a
 * natural number greater than 1 that cannot be formed by multiplying two
 * smaller natural numbers.
 * <p>
 * 
 * Collection contains the first n prime numbers (n is the size of the
 * collection).
 * 
 * @author Filip Karacic
 *
 */
public class PrimesCollection implements Iterable<Integer> {
	/**
	 * Size of this collection.
	 */
	private int size;

	/**
	 * Initializes newly created {@code PrimesCollection} object representing
	 * collection of the first {@code size} primes.
	 * 
	 * @param size
	 *            size of the collection
	 * @throws IllegalArgumentException
	 *             if the given size is not positive integer
	 */
	public PrimesCollection(int size) {
		if (size < 1)
			throw new IllegalArgumentException("Number of prime numbers must be positive integer.");

		this.size = size;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new PrimesIterator();
	}

	/**
	 * Iterator for this collection of primes.
	 */
	private class PrimesIterator implements Iterator<Integer> {
		/**
		 * Current prime.
		 */
		private int current;
		/**
		 * Next prime.
		 */
		private int next;
		/**
		 * Size of this collection.
		 */
		private int size;
		/**
		 * The first prime.
		 */
		private static final int FIRST_PRIME = 2;

		/**
		 * Initializes newly created iterator for this collection of prime numbers.
		 */
		private PrimesIterator() {
			current = next = FIRST_PRIME;
			this.size = PrimesCollection.this.size;
		}

		@Override
		public boolean hasNext() {
			return size > 0;
		}

		@Override
		public Integer next() {
			if (size <= 0)
				throw new NoSuchElementException("No more primes in this collection.");

			current = next;

			next = countNext(current);
			size--;

			return current;
		}

		/**
		 * Returns the first prime number after the given prime number.
		 * 
		 * @param current
		 *            current prime number.
		 * @return the first prime number after the given prime number
		 */
		private int countNext(int current) {
			boolean prime = false;

			while (!prime) {
				current++;

				if (current % 2 == 0)
					continue;

				prime = true;
				for (int i = 2; i <= Math.sqrt(current); i++) {
					if (current % i == 0) {
						prime = false;
						break;
					}
				}
			}

			return current;
		}

	}

}
