package hr.fer.zemris.java.namebuilder.lexer;

/**
 * {@code LexerState} represents states of the lexer. States are TEXT AND
 * SUBSTITUTION. In TEXT (i.e. outside of substitution) lexer execpts every
 * character until sequence '${' is reached.
 * <p>
 * In substitution lexer accepts everything until '}' is reached. Additionaly,
 * valid form of the substitution is number representing group and can also
 * contain description of the group seperated by the comma from the group
 * number.
 * 
 * @author Filip Karacic
 *
 */
public enum LexerState {

	/**
	 * State of reading plain text.
	 */
	TEXT,

	/**
	 * Substitution state.
	 */
	SUBSTITUTION;
}
