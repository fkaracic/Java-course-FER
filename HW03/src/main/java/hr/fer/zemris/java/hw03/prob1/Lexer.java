package hr.fer.zemris.java.hw03.prob1;

import java.util.Objects;

/**
 * {@code Lexer} represents lexical analysis,i.e. the process of converting a
 * sequence of characters into a sequence of tokens. Text contains sequences of
 * words, numbers and symbols. Word is every sequence of one or more characters
 * where method Character.isLetter returns {@code true}. Number is every
 * sequence of one or more digits and is in range for {@code long}. Symbol is
 * every single character that we get after removing all of the words, numbers
 * and blanks('\r', '\n', '\t', ' '). Blanks are ignored. Token of type EOF is
 * generated as last one in processing.
 * 
 * @author Filip Karacic
 *
 */
public class Lexer {

	/**
	 * Input text.
	 */
	private char[] data;

	/**
	 * Current token.
	 */
	private Token token;

	/**
	 * Index of the first unprocessed sign.
	 */
	private int currentIndex;

	/**
	 * State of the lexer. Default state is BASIC.
	 */
	private LexerState state;
	
	private static final char STATE_CHANGER = '#';

	/**
	 * Initialize newly created object representing a lexer performing a lexical
	 * analysis.
	 * 
	 * @param text
	 *            text to be analysed lexically
	 * @throws NullPointerException if {@code text} is {@code null}.
	 */
	public Lexer(String text) {
		Objects.requireNonNull(text);

		data = text.toCharArray();
		currentIndex = 0;
		state = LexerState.BASIC;
	}

	/**
	 * Returns current token.
	 * 
	 * @return current token.
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Sets the state of this lexer to given state.
	 * 
	 * @param state
	 *            state of the lexer.
	 * @throws NullPointerException
	 *             if {@code state} is {@code null}
	 */
	public void setState(LexerState state) {
		Objects.requireNonNull(state);

		this.state = state;
	}

	/**
	 *
	 * Returns the next token after lexical analysis. Returns token of type EOF if
	 * end of input is reached.
	 * 
	 * @return next token after lexical analysis
	 * @throws LexerException
	 *             If lexically invalid input is given or method is called after the
	 *             end of file. Also if number is out of range for {@code long}.
	 */
	public Token nextToken() {

		if (token != null && token.getType() == TokenType.EOF) {
			throw new LexerException("No more inputs");
		}

		skipBlanks();

		if (currentIndex >= data.length) {
			return token = new Token(TokenType.EOF, null);
		}

		if (state == LexerState.BASIC) {
			if (Character.isLetter(data[currentIndex]) || isEscape()) {
				extractWord();
			} else if (Character.isDigit(data[currentIndex])) {
				extractNumber();
			} else {
				extractSymbol();
			}
		} else {
			if (Character.isLetter(data[currentIndex]) || Character.isDigit(data[currentIndex]) || isEscape()) {
				extendedExtractWord();
			} else {
				extractSymbol();
			}

		}

		return token;
	}

	private boolean isEscape() {
		return data[currentIndex] == '\\';
	}

	/**
	 * Creates a token of type SYMBOL. If symbol is '#' state is changed to
	 * different from current.
	 */
	private void extractSymbol() {

		if (data[currentIndex] == STATE_CHANGER) {
			setState(state == LexerState.BASIC ? LexerState.EXTENDED : LexerState.BASIC);
		}

		token = new Token(TokenType.SYMBOL, Character.valueOf(data[currentIndex++]));
	}

	/**
	 * Creates a token of type NUMBER.
	 * 
	 * @throws LexerException
	 *             if number is out of range for long
	 */
	private void extractNumber() {
		StringBuilder number = new StringBuilder();

		while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
				number.append(data[currentIndex++]);
		}

		try {
			Long num = Long.parseLong(number.toString());
			token = new Token(TokenType.NUMBER, num);
		} catch (NumberFormatException e) {
			throw new LexerException("Number is out of range for long.");
		}
	}

	/**
	 * Creates a token of type WORD. Lexer works in a BASIC state.
	 */
	private void extractWord() {
		StringBuilder word = new StringBuilder();

		while (currentIndex < data.length) {
			if (Character.isLetter(data[currentIndex])) {
				word.append(data[currentIndex++]);
			} else if (isEscape()){
				currentIndex++;
				if(!isValidEscape()) throw new LexerException();
				
				word.append(data[currentIndex++]);
			}else {
				break;
			}

		}

		token = new Token(TokenType.WORD, word.toString());

	}

	private boolean isValidEscape() {
		return currentIndex < data.length && ( data[currentIndex] == '\\' || Character.isDigit(data[currentIndex]));
	}

	/**
	 * Creates a token of type WORD. Lexer works in an EXTENDED state.
	 */
	private void extendedExtractWord() {
		StringBuilder word = new StringBuilder();

		while (currentIndex < data.length) {

			switch (data[currentIndex]) {
			case ' ':
			case '\n':
			case '\t':
			case '\r':
			case STATE_CHANGER:
				token = new Token(TokenType.WORD, word.toString());
				return;
			default:
				word.append(data[currentIndex++]);
				break;
			}
		}
	}

	/**
	 * Skips all of the blanks and stop when it gets to the character or end of the
	 * file.
	 */
	private void skipBlanks() {
		while (currentIndex < data.length) {
			switch (data[currentIndex]) {
			case ' ':
			case '\n':
			case '\t':
			case '\r':
				currentIndex++;
				continue;
			default:
				return;
			}
		}
	}
}