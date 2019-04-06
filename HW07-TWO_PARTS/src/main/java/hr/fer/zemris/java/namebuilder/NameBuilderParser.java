package hr.fer.zemris.java.namebuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.namebuilder.lexer.Lexer;
import hr.fer.zemris.java.namebuilder.lexer.LexerException;
import hr.fer.zemris.java.namebuilder.lexer.Token;
import hr.fer.zemris.java.namebuilder.lexer.TokenType;

/**
 * {@code NameBuilderParser} represents parser with the following rules.
 * <p>
 * 
 * A one or more spaces (tabs, enters or spaces) will be threaten equally.
 * Substitution part starts with the following sequence '${' and ends with the
 * '}'. Everything else is plain text.
 * 
 * @author Filip Karacic
 *
 */
public class NameBuilderParser {
	/**
	 * Lexer for this parser.
	 */
	Lexer lexer;
	/**
	 * List of the name builders created from parsing given text.
	 */
	List<NameBuilder> list;

	/**
	 * Initializes newly created {@code NameBuilderParser} representing parser for
	 * name builders.
	 * 
	 * @param expression
	 *            expression to be parsed
	 * 
	 * @throws NameBuilderParserException
	 *             if the given expression is not valid
	 * @throws NullPointerException
	 *             if the given expression is <code>null</code>
	 */
	public NameBuilderParser(String expression) {
		Objects.requireNonNull(expression);

		list = new ArrayList<>();
		lexer = new Lexer(expression);

		parse(expression);
	}

	/**
	 * Returns name builder containing all name builders created from parsing the
	 * given expression.
	 * 
	 * @return name builder containing all name builders created from parsing the
	 *         given expression
	 * 
	 */
	public NameBuilder getNameBuilder() {
		return new NameBuilderAll(list);
	}

	/**
	 * Parses the given expression and creates {@code NameBuilder} objects that are
	 * stored in the list.
	 * 
	 * @param expression
	 *            expression to be parsed
	 * 
	 * @throws NameBuilderParserException
	 *             if the given expression is invalid.
	 */
	private void parse(String expression) {
		if (expression.isEmpty())
			throw new NameBuilderParserException("Empty expression is not allowed.");

		Token token;

		try {
			while (true) {
				token = lexer.nextToken();

				if (token.getType() == TokenType.EOF)
					break;

				if (token.getType() == TokenType.TEXT) {
					list.add(new NameBuilderString(token.getValue()));
					continue;
				}

				if (token.getType() == TokenType.SUBSTART) {
					substitution(token);
				}

			}
		} catch (LexerException e) {
			throw new NameBuilderParserException(e);
		}
	}

	/**
	 * Parses substitution input.
	 * 
	 * @param token
	 *            last token returned from lexer of this parser
	 * 
	 * @throws NameBuilderParserException
	 *             if the input is not valid substitution input
	 */
	private void substitution(Token token) {
		token = lexer.nextToken();

		if (token.getType() != TokenType.GROUPNUM)
			throw new NameBuilderParserException();

		String group = token.getValue();

		token = lexer.nextToken();

		if (token.getType() == TokenType.SUBEND) {
			list.add(new NameBuilderGroup(Integer.parseInt(group), null));
		} else if (token.getType() == TokenType.GROUPDESC) {
			String groupDesc = token.getValue();

			token = lexer.nextToken();

			if (token.getType() != TokenType.SUBEND) {
				throw new NameBuilderParserException();
			}

			list.add(new NameBuilderGroup(Integer.parseInt(group), groupDesc));
		} else {
			throw new NameBuilderParserException();
		}

	}

	/**
	 * Represents name builder containing plain text.
	 *
	 */
	private class NameBuilderString implements NameBuilder {
		/**
		 * Text of this name builder.
		 */
		String text;

		/**
		 * Initializes newly created object.
		 * 
		 * @param text
		 *            text for this name builder
		 * @throws NullPointerException
		 *             if the given text is <code>null</code>
		 */
		private NameBuilderString(String text) {
			this.text = Objects.requireNonNull(text);
		}

		@Override
		public void execute(NameBuilderInfo info) {
			Objects.requireNonNull(info).getStringBuilder().append(text);
		}

	}

	/**
	 * Represents name builder containing group informations.
	 *
	 */
	private class NameBuilderGroup implements NameBuilder {
		/**
		 * Group number.
		 */
		int groupNumber;
		/**
		 * Description of the group. Optional (can be <code>null</code>).
		 */
		String minimalWidth;

		/**
		 * Initializes newly created object representing name builder for groups
		 * 
		 * @param groupNumber
		 *            group number
		 * @param minimalWidth
		 *            group description
		 */
		private NameBuilderGroup(int groupNumber, String minimalWidth) {
			this.groupNumber = groupNumber;
			this.minimalWidth = minimalWidth;
		}

		@Override
		public void execute(NameBuilderInfo info) {
			Objects.requireNonNull(info);

			String text = info.getGroup(groupNumber);
			int textLength = text.length();

			int minimalLength;

			if (minimalWidth != null && (minimalLength = Integer.parseInt(minimalWidth)) > textLength) {
				text = minimalWidth.startsWith("0")
						? String.format("%0" + (minimalLength - textLength) + "d%s", 0, text)
						: String.format("%" + minimalWidth + "s", text);
			}

			info.getStringBuilder().append(text);
		}

	}

	/**
	 * Represents name builder containing all name builders created from parsing the
	 * given expression to the parser.
	 *
	 */
	private class NameBuilderAll implements NameBuilder {
		/**
		 * List of name builders created from parsing the given expression.
		 */
		List<NameBuilder> nameBuilders;

		/**
		 * Initializes newly created object.
		 * 
		 * @param nameBuilders
		 *            list of name builders
		 */
		private NameBuilderAll(List<NameBuilder> nameBuilders) {
			this.nameBuilders = new ArrayList<>(nameBuilders);
		}

		@Override
		public void execute(NameBuilderInfo info) {
			for (NameBuilder nameBuilder : nameBuilders) {
				nameBuilder.execute(info);
			}
		}

	}

}
