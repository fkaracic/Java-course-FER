package hr.fer.zemris.java.hw06.demo2;

/**
 * Program demonstrates usage of collection of prime numbers. Prints the first
 * five prime numbers.
 * 
 * @author Filip Karacic
 *
 */
public class PrimesDemo2 {

	/**
	 * Method called when this program starts.
	 * 
	 * @param args
	 *            command line arguments represented as array of {@code String}
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(5);
		for (Integer prime : primesCollection) {
			System.out.println("Got prime: " + prime);
		}

	}
}
