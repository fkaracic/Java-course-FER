package hr.fer.zemris.java.namebuilder.lexer;

import java.util.Objects;

/**
 * {@code Lexer} represents lexical analysis,i.e. the process of converting a
 * sequence of characters into a sequence of tokens. Text contains sequences of
 * words, tags, numbers, variables, functions, operators. Token of type EOF is
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
	private int index;
	/**
	 * State of the lexer. The default state is UNKOWN.
	 */
	private LexerState state;

	/**
	 * Initialize newly created object representing a lexer performing a lexical
	 * analysis.
	 * 
	 * @param text
	 *            text to be analysed lexically
	 * @throws NullPointerException
	 *             if {@code text} is {@code null}.
	 */
	public Lexer(String text) {
		Objects.requireNonNull(text);

		index = 0;
		state = LexerState.TEXT;
		data = text.toCharArray();
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
	 *
	 * Returns the next token after lexical analysis. Returns token of type EOF if
	 * end of input is reached.
	 * 
	 * @return next token after lexical analysis
	 * @throws LexerException
	 *             If lexically invalid input is given or method is called after the
	 *             end of file.
	 */
	public Token nextToken() {
		if (token != null && token.getType() == TokenType.EOF) {
			throw new LexerException("Cannot get next token. No more text to process.");
		}

		if (index >= data.length) {
			return token = new Token(TokenType.EOF, null);
		}

		if (isSubStart()) {
			state = LexerState.SUBSTITUTION;
		}

		if (state == LexerState.TEXT) {
			return token = extractText();
		}

		skipBlanks();

		return extractToken();
	}

	/**
	 * Returns token created from tokenization.
	 * 
	 * @return token created from tokenization
	 */
	private Token extractToken() {
		if (isSubStart()) {
			index += 2;
			return token = new Token(TokenType.SUBSTART, "${");
		}

		if (data[index] == '}') {
			index++;
			state = LexerState.TEXT;
			return token = new Token(TokenType.SUBEND, "}");
		}

		if (Character.isDigit(data[index])) {
			String number = extractDigit();
			return token = new Token(TokenType.GROUPNUM, number);
		}

		if (data[index] == ',') {
			index++;
			String number = extractDigit();
			return token = new Token(TokenType.GROUPDESC, number);
		}

		else {
			throw new LexerException("Invalid expression.");
		}
	}

	/**
	 * Extracts digit from the substitution.
	 * 
	 * @return digit as {@code String}
	 * @throws LexerException
	 *             if digit is not valid
	 */
	private String extractDigit() {
		StringBuilder number = new StringBuilder();

		while (index < data.length) {
			if (data[index] == ',' || data[index] == '}') {
				break;
			} else if (Character.isDigit(data[index])) {
				number.append(data[index++]);
			} else {
				throw new LexerException("Invalid character.");
			}
		}

		try {
			Integer.parseInt(number.toString());
			return number.toString();
		} catch (NumberFormatException e) {
			throw new LexerException(e);
		}

	}

	/**
	 * Creates a token of type TEXT. Lexer works in a TEXT state.
	 * 
	 * 
	 * @return token of type TEXT
	 * @throws LexerException
	 *             if text is not valid
	 */
	private Token extractText() {
		StringBuilder text = new StringBuilder();

		while (index < data.length) {
			if (isSubStart()) {
				state = LexerState.SUBSTITUTION;
				break;
			}
			text.append(data[index++]);

		}

		return new Token(TokenType.TEXT, text.toString());
	}

	/**
	 * Returns {@code true} if sequence is substitution start (i.e. '${').
	 * 
	 * @return {@code true} if sequence is substitution start
	 */
	private boolean isSubStart() {
		return index + 1 < data.length && data[index] == '$' && data[index + 1] == '{';
	}

	/**
	 * Skips all of the blanks in the tag between two tokens.
	 * 
	 */
	private void skipBlanks() {
		while (index < data.length) {
			switch (data[index]) {
			case ' ':
			case '\t':
				index++;
				break;
			default:
				return;
			}
		}
	}
}
