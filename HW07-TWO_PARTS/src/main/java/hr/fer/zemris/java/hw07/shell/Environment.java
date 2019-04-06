package hr.fer.zemris.java.hw07.shell;

import java.nio.file.Path;
import java.util.SortedMap;

/**
 * Interface {@code Environment} describes environment of the shell. It is used
 * for communication with the user.
 * 
 * @author Filip Karacic
 *
 */
public interface Environment {

	/**
	 * Reads line user enters.
	 * 
	 * @return read line
	 * @throws ShellIOException
	 *             if there is error reading line
	 */
	String readLine() throws ShellIOException;

	/**
	 * Writes given text.
	 * 
	 * @param text
	 *            text to be written
	 * @throws ShellIOException
	 *             if there is error writing line
	 */
	void write(String text) throws ShellIOException;

	/**
	 * Writes given text and the next line at the end.
	 * 
	 * @param text
	 *            text to be written
	 * @throws ShellIOException
	 *             if there is error writing line
	 */
	void writeln(String text) throws ShellIOException;

	/**
	 * Returns map of valid commands.
	 * 
	 * @return map of valid commands
	 */
	SortedMap<String, ShellCommand> commands();

	/**
	 * Return multiline symbol
	 * 
	 * @return multiline symbol
	 */
	Character getMultilineSymbol();

	/**
	 * Sets multiline symbol
	 * 
	 * @param symbol
	 *            symbol
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * Returns prompt symbol
	 * 
	 * @return prompt symbol
	 */
	Character getPromptSymbol();

	/**
	 * Sets prompt symbol
	 * 
	 * @param symbol
	 *            symbol
	 */
	void setPromptSymbol(Character symbol);

	/**
	 * Returns morelines symbol
	 * 
	 * @return morelines symbol
	 */
	Character getMorelinesSymbol();

	/**
	 * Sets morelines symbol
	 * 
	 * @param symbol
	 *            symbol to be set
	 */
	void setMorelinesSymbol(Character symbol);

	/**
	 * Returns current directory.
	 * 
	 * @return current directory
	 */
	Path getCurrentDirectory();

	/**
	 * Sets current directory.
	 * 
	 * @param path
	 *            path to be set
	 */
	void setCurrentDirectory(Path path);

	/**
	 * Returns value stored within the given key.
	 * 
	 * @param key
	 *            key within value is stored
	 * @return value stored within the given key, or <code>null</code> if there is
	 *         no such value
	 */
	Object getSharedData(String key);

	/**
	 * Puts value mapped to the given key into the map containing shared data.
	 * 
	 * @param key key for the given value
	 * @param value value to be stored
	 */
	void setSharedData(String key, Object value);

}
