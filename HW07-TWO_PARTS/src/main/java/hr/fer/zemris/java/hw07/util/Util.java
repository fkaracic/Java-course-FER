package hr.fer.zemris.java.hw07.util;

/**
 * {@code Util} class is used for conversion of bytes to hexadecimal and
 * hexadecimal to bytes.
 * 
 * @author Filip Karacic
 *
 */
public class Util {
	/**
	 * Radix refers to base number (the number of digits in the number system). This
	 * radix is for hexadecimal (base 16).
	 */
	private static final int RADIX = 16;

	/**
	 * Converts given {@code String} from hexadecimal to byte.
	 * 
	 * @param keyText
	 *            hexadecimal-encoded {@code String}
	 * @return array of {@code byte} created from the given text
	 * 
	 * @throws IllegalArgumentException
	 *             if the given text is not representing an odd number of
	 *             hexadecimal characters.
	 */
	public static byte[] hexToByte(String keyText) {
		if (keyText == null || keyText.length() % 2 != 0) {
			throw new IllegalArgumentException("The given text must be hex-encoded text.");
		}

		byte[] result = new byte[keyText.length() >> 1];

		for (int i = 0, h = keyText.length(), j = 0; i < h; i += 2, j++) {
			int first = Character.digit(keyText.charAt(i), RADIX);
			int second = Character.digit(keyText.charAt(i + 1), RADIX);

			if (first == -1 || second == -1)
				throw new IllegalArgumentException("Invalid character. Only hexadecimal characters are allowed.");

			result[j] = (byte) first;
			result[j] <<= 4;
			result[j] += (byte) second;
		}

		return result;
	}

	/**
	 * Converts array of bytes to hexadecimal {@code String}.
	 * 
	 * @param hex
	 *            array of bytes
	 * @return {@code String} converted from the given array of bytes
	 * 
	 * @throws IllegalArgumentException
	 *             if the given array is <code>null</code>
	 */
	public static String byteToHex(byte[] hex) {
		if (hex == null)
			throw new IllegalArgumentException("Array of bytes must be given.");

		StringBuilder hexadecimal = new StringBuilder();

		for (byte a : hex) {
			hexadecimal.append(String.format("%02x", a));
		}

		return hexadecimal.toString();
	}

}