package hr.fer.zemris.java.hw07.utility;

import java.util.Objects;

import hr.fer.zemris.java.hw07.shell.Environment;

/**
 * Class {@code SplitUtility} is used for splitting arguments from the given
 * text. Text in between quotation marks can contain spaces.
 * 
 * @author Filip Karacic
 *
 */
public class SplitUtility {
	/**
	 * Array containing characters of the given text.
	 */
	private static char[] data;
	/**
	 * Index of the current character.
	 */
	private static int index;

	/**
	 * Splits given text by spaces unless text is in quotation marks where spaces
	 * are allowed. Text in quotation marks is representing one argument.
	 * 
	 * @param text
	 *            text to be split
	 * @param env
	 *            environment
	 * @return array of arguments or <code>null</code> if the given text is not
	 *         valid
	 */
	public static String[] splitArguments(String text, Environment env) {
		Objects.requireNonNull(text, "Provided text must not be null.");
		Objects.requireNonNull(env, "Provided environment must not be null.");

		text = text.trim();
		data = text.toCharArray();
		index = 0;
		StringBuilder arguments = new StringBuilder();

		while (index < data.length) {

			if (data[index] == '\"') {
				arguments = stringExtract(arguments);

				if (arguments == null)
					return null;

			} else if (data[index] == ' ') {
				arguments.append(";");
				index++;
				skipOtherBlanks();
			} else {
				arguments.append(data[index++]);
			}
		}

		return arguments.toString().split(";");
	}

	/**
	 * Skips blanks.
	 */
	private static void skipOtherBlanks() {
		while (index < data.length) {
			switch (data[index]) {
			case ' ':
			case '\t':
				index++;
			default:
				return;
			}
		}

	}

	/**
	 * Extracts text within the quotation marks.
	 * 
	 * @param arguments
	 *            builder used for adding string literal
	 * @return builder containing string literal
	 */
	private static StringBuilder stringExtract(StringBuilder arguments) {
		index++;

		while (index < data.length) {
			if (data[index] == '\\') {
				if (index + 1 < data.length && data[index + 1] == '\"') {
					index++;
					arguments.append(data[index++]);
				} else if (index + 1 < data.length && data[index + 1] == '\\') {
					index++;
					arguments.append(data[index++]);
				} else {
					arguments.append(data[index++]);
				}
			} else if (data[index] == '\"') {
				break;
			} else {
				arguments.append(data[index++]);
			}
		}

		if (index == data.length || (index + 1) != data.length && data[index + 1] != ' ')
			return null;

		index++;
		return arguments;
	}

}
