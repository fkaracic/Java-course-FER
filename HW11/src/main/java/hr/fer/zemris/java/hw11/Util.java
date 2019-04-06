package hr.fer.zemris.java.hw11;

/**
 * Has one static method for reversing letter cases.
 * 
 * @author Filip Karacic
 *
 */
public class Util {

	/**
	 * Reverses case of every letter from the given text. E.g. 'A' becomes 'a' and
	 * 'b' becomes 'B'.
	 * 
	 * @param text
	 *            text whose letters are to be reversed
	 * 
	 * @return text whose letters are reversed
	 */
	public static String reverseCase(String text) {
		char[] chars = text.toCharArray();

		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (Character.isUpperCase(c)) {
				chars[i] = Character.toLowerCase(c);
			} else if (Character.isLowerCase(c)) {
				chars[i] = Character.toUpperCase(c);
			}
		}

		return new String(chars);
	}

}
