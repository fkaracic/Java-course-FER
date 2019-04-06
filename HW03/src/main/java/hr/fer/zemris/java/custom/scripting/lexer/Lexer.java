package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.Objects;

import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * 
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
	private int currentIndex;
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

		currentIndex = 0;
		state = LexerState.DOCUMENT_TEXT;
		data = text.toCharArray();
	}

	/**
	 * Sets state of the lexer to the given value.
	 * 
	 * @param state
	 *            new state of lexer
	 * @throws NullPointerException
	 *             if {@code state} is {@code null}
	 */
	private void setState(LexerState state) {
		Objects.requireNonNull(state);
		this.state = state;
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

		if (currentIndex >= data.length) {
			return token = new Token(TokenType.EOF, null);
		}

		if (isTagStart()) {
			setState(LexerState.TAG);
		}

		if (state == LexerState.DOCUMENT_TEXT) {
			return token = extractText();
		}

		skipBlanks();

		if (isTagStart()) {
			currentIndex += 2;
			return token = new Token(TokenType.TAGSTART, new ElementString("{$"));
		}

		if (isTagEnd()) {
			currentIndex += 2;
			setState(LexerState.DOCUMENT_TEXT);
			return token = new Token(TokenType.TAGEND, new ElementString("$}"));
		}

		if (Character.isLetter(data[currentIndex])) {
			extractVariable();
			return token;
		}

		if (data[currentIndex] == '=') {

			return token = new Token(TokenType.VARIABLE, new ElementVariable(Character.toString(data[currentIndex++])));
		}

		if (Character.isDigit(data[currentIndex])) {
			extractDigit();
			return token;
		}

		if (isOperator()) {
			extractOperatorOrNumber();
			return token;
		}

		if (data[currentIndex] == '@') {
			extractFunction();
			return token;
		}

		if (data[currentIndex] == '\"') {
			extractString();
			return token;
		}

		if (data[currentIndex] == '\\') {
			throw new LexerException("Invalid expression in tag. Was: '" + data[currentIndex] + "'.");
		} else {
			throw new LexerException("Invalid expression.");
		}
	}

	private void extractString() {
		StringBuilder string = new StringBuilder();

		currentIndex++;
		while (currentIndex < data.length) {
			if (data[currentIndex] == '\\') {
				if (data[currentIndex + 1] == '\\' || data[currentIndex + 1] == '\"') {
					currentIndex++;
					string.append(data[currentIndex++]);
				} else {
					throw new LexerException("Invalid escaping in string. '\\\\' and '\\\"' are the only allowed.");
				}
			} else if (data[currentIndex] == '\"') {
				currentIndex++;
				break;
			} else {
				string.append(data[currentIndex++]);
			}
		}

		token = new Token(TokenType.STRING, new ElementString(string.toString()));
		
	}

	private void extractFunction() {
		StringBuilder function = new StringBuilder();

		function.append(data[currentIndex++]);

		while (currentIndex < data.length) {
			if (isBlank() || data[currentIndex] == '$')
				break;

			function.append(data[currentIndex++]);
		}

		if (!validName(function.toString().substring(1)))
			throw new LexerException("Invalid function name! Was: " + function.toString());

		token = new Token(TokenType.FUNCTION, new ElementFunction(function.toString()));
		
	}

	private void extractOperatorOrNumber() {
		if (data[currentIndex] == '-' && Character.isDigit(data[currentIndex + 1])) {
			StringBuilder number = new StringBuilder();
			number.append(data[currentIndex++]);

			while (currentIndex < data.length) {
				if (Character.isDigit(data[currentIndex]) || data[currentIndex] == '.') {
					number.append(data[currentIndex++]);
				} else
					break;
			}

			try {
				token = numberToken(number.toString());
			} catch (LexerException e) {
				throw new LexerException(e.getMessage(), e);
			}

		} else {
			token = new Token(TokenType.OPERATOR,
					new ElementOperator(Character.toString(data[currentIndex++])));
		}
		
	}

	private void extractDigit() {
		StringBuilder number = new StringBuilder();

		while (currentIndex < data.length) {
			if (data[currentIndex] == '.' || Character.isDigit(data[currentIndex])) {
				number.append(data[currentIndex++]);
			} else
				break;
		}

		try {
			token = numberToken(number.toString());
		} catch (LexerException e) {
			throw new LexerException(e.getMessage(), e);
		}
		
	}

	private void extractVariable() {
		StringBuilder name = new StringBuilder();

		while (currentIndex < data.length) {
			if (isBlank() || data[currentIndex] == '$' || data[currentIndex] == '\"')
				break;
			name.append(data[currentIndex++]);
		}

		if (!validName(name.toString()))
			throw new LexerException("Invalid variable name. Was: " + name.toString());

		token = new Token(TokenType.VARIABLE, new ElementVariable(name.toString()));
	}

	/**
	 * Creates a token of type TEXT. Lexer works in a DOCUMENT_TEXT state.
	 * 
	 * 
	 * @return token of type TEXT
	 * @throws LexerException
	 *             if text is not valid
	 */
	private Token extractText() {
		StringBuilder text = new StringBuilder();

		while (currentIndex < data.length) {
			if (isTagStart()) {
				setState(LexerState.TAG);
				break;
			}

			if (data[currentIndex] == '\\') {
				if (!textValid())
					throw new LexerException(
							"Invalid escaping in document text. '\\\\' and '\\{' are the only allowed.");

				currentIndex++;
				text.append(data[currentIndex++]);
			} else {
				text.append(data[currentIndex++]);
			}
		}

		return new Token(TokenType.TEXT, new ElementString(text.toString()));
	}

	/**
	 * Returns {@code true} if text has valid escaping.
	 * 
	 * @return {@code true} if text has valid escaping
	 */
	private boolean textValid() {
		int i = currentIndex + 1;

		return i < data.length && (data[i] == '\\' || data[i] == '{');
	}

	/**
	 * Returns {@code true} if character is one of the allowed operators.
	 * 
	 * @return {@code true} if character is one of the allowed operators
	 */
	private boolean isOperator() {
		switch (data[currentIndex]) {
		case '+':
		case '-':
		case '*':
		case '/':
		case '^':
			return true;
		default:
			return false;
		}
	}

	/**
	 * Returns {@code true} if current character is a blank.
	 * 
	 * @return {@code true} if current character is a blank
	 */
	private boolean isBlank() {
		switch (data[currentIndex]) {
		case '\n':
		case ' ':
		case '\r':
		case '\t':
			return true;
		default:
			return false;
		}
	}

	/**
	 * Returns {@code true} if sequence is tag start (i.e. '{$').
	 * 
	 * @return {@code true} if sequence is tag start
	 */
	private boolean isTagStart() {
		int i = currentIndex;

		return i + 1 < data.length && data[i] == '{' && data[++i] == '$';
	}

	/**
	 * Returns {@code true} if sequence is tag end (i.e. '$}').
	 * 
	 * @return {@code true} if sequence is tag end
	 */
	private boolean isTagEnd() {
		int i = currentIndex;

		return i + 1 < data.length && data[i] == '$' && data[++i] == '}';
	}

	/**
	 * Returns {@code true} if {@code name} is valid name by the rules of this
	 * lexer.
	 * 
	 * @param name
	 *            name to be tested
	 * @return {@code true} if {@code name} is valid name
	 */
	private boolean validName(String name) {
		if (name == null || name.isEmpty())
			return false;

		if (!Character.isLetter(name.charAt(0)))
			return false;

		for (int i = 1, h = name.length(); i < h; i++) {
			if (name.charAt(i) != '_' && !Character.isDigit(name.charAt(i)) && !Character.isLetter(name.charAt(i)))
				return false;
		}

		return true;
	}

	/**
	 * Skips all of the blanks in the tag between two tokens.
	 * 
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

	/**
	 * Returns token represented by {@code number}.
	 * 
	 * @param number
	 *            number to be tokenized
	 * @return token represented by {@code number}
	 * 
	 * @throws NullPointerException
	 *             if {@code number} is {@code null}
	 * @throws LexerException
	 *             if {@code number} is not valid number
	 */
	private Token numberToken(String number) {
		Objects.requireNonNull(number);

		if (number.contains(".")) {
			try {
				return token = new Token(TokenType.NUMBER, new ElementConstantDouble(Double.parseDouble(number)));
			} catch (NumberFormatException e) {
				throw new LexerException("Invalid decimal number. Was: " + number);
			}
		} else {
			try {
				return token = new Token(TokenType.NUMBER, new ElementConstantInteger(Integer.parseInt(number)));
			} catch (NumberFormatException e) {
				throw new LexerException("Invalid integer. Was: " + number);
			}
		}
	}
}
