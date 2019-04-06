package hr.fer.zemris.java.hw03.prob1;

/**
 * {@code LexerState} represents state of the lexer. BASIC state: text contains
 * sequences of words, numbers and symbols. Word is every sequence of one or
 * more characters where method Character.isLetter returns {@code true}. Number
 * is every sequence of one or more digits and is in range for {@code long}.
 * Symbol is every single character that we get after removing all of the words,
 * numbers and blanks('\r', '\n', '\t', ' '). Blanks are ignored. When character
 * '#' is read lexer transfers to EXTENDED state in which everything is interpreted
 * as a single word. In EXTENDED state there is no escaping.
 * 
 * @author Filip Karacic
 *
 */
public enum LexerState {
	/**
	 * Basic state.
	 */
	BASIC, 
	
	/**
	 * Extended state.
	 */
	EXTENDED;
}
