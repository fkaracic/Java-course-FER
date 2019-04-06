package hr.fer.zemris.java.hw06.demo2;

/**
 * Program demonstrates usage of collection of prime numbers. Prints all of the
 * pairs of the first two prime numbers.
 * 
 * @author Filip Karacic
 *
 */
public class PrimesDemo1 {

	/**
	 * Method called when this program starts.
	 * 
	 * @param args
	 *            command line arguments represented as array of {@code String}
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(2);

		for (Integer prime : primesCollection) {
			for (Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: " + prime + ", " + prime2);
			}
		}

	}
}
