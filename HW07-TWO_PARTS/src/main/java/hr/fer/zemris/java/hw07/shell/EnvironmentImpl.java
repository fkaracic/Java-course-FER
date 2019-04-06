package hr.fer.zemris.java.hw07.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.Objects;

/**
 * Implementation of the environment for the shell. It is used for communication
 * between user and the commands.
 * 
 * @author Filip Karacic
 *
 */
public class EnvironmentImpl implements Environment {
	/**
	 * Available commands for this environment.
	 */
	private SortedMap<String, ShellCommand> commands;

	/**
	 * Reader of the inputs.
	 */
	private BufferedReader read;
	/**
	 * Writer of the outputs.
	 */
	private BufferedWriter write;

	/**
	 * Prompt symbol
	 */
	private static char PROMPT_SYMBOL = '>';
	/**
	 * MultiLine symbol
	 */
	private static char MULTILINE_SYMBOL = '|';
	/**
	 * More lines symbol
	 */
	private static char MORELINES_SYMBOL = '\\';

	/**
	 * Current working directory.
	 */
	private Path currentDirectory;

	/**
	 * Map of shared data for this environment.
	 */
	private Map<String, Object> sharedData;

	/**
	 * Initializes newly created {@code EnvironmentImpl} object representing the
	 * environment.
	 * 
	 * @param commands
	 *            map of available commands
	 * @param currentDirectory
	 *            current working directory
	 * @param reader
	 *            input stream for the user inputs
	 * @param writer
	 *            output stream for the user outputs
	 * 
	 * @throws NullPointerException
	 *             if any of the given arguments is <code>null</code>
	 */
	public EnvironmentImpl(SortedMap<String, ShellCommand> commands, Path currentDirectory, InputStreamReader reader,
			OutputStreamWriter writer) {

		read = new BufferedReader(Objects.requireNonNull(reader));
		write = new BufferedWriter(Objects.requireNonNull(writer));

		this.commands = Collections.unmodifiableSortedMap(Objects.requireNonNull(commands));
		this.currentDirectory = Objects.requireNonNull(currentDirectory).toAbsolutePath().normalize();

		sharedData = new HashMap<>();
	}

	@Override
	public String readLine() throws ShellIOException {

		try {
			return read.readLine();
		} catch (IOException e) {
			throw new ShellIOException("Error reading from system input.");
		}
	}

	@Override
	public void write(String text) throws ShellIOException {
		Objects.requireNonNull(text);

		try {
			write.write(text);
			write.flush();
		} catch (IOException e) {
			throw new ShellIOException("Error writing to system output.");
		}

	}

	@Override
	public void writeln(String text) throws ShellIOException {
		Objects.requireNonNull(text);

		try {
			write.write(text);
			write.newLine();
			write.flush();
		} catch (IOException e) {
			throw new ShellIOException("Error writing to system output.");
		}

	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return commands;
	}

	@Override
	public Character getMultilineSymbol() {
		return MULTILINE_SYMBOL;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		MULTILINE_SYMBOL = Objects.requireNonNull(symbol);

	}

	@Override
	public Character getPromptSymbol() {
		return PROMPT_SYMBOL;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		PROMPT_SYMBOL = Objects.requireNonNull(symbol);

	}

	@Override
	public Character getMorelinesSymbol() {
		return MORELINES_SYMBOL;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		MORELINES_SYMBOL = Objects.requireNonNull(symbol);

	}

	@Override
	public Path getCurrentDirectory() {
		return currentDirectory;
	}

	@Override
	public void setCurrentDirectory(Path path) {
		File dir = Objects.requireNonNull(path).toFile();

		if (!dir.isDirectory() || !dir.exists())
			throw new IllegalArgumentException("Invalid path given. Must be existing directory.");

		currentDirectory = path.toAbsolutePath().normalize();
	}

	@Override
	public Object getSharedData(String key) {
		if (key == null)
			return null;

		return sharedData.get(key);
	}

	@Override
	public void setSharedData(String key, Object value) {
		Objects.requireNonNull(key);
		Objects.requireNonNull(value);

		sharedData.put(key, value);
	}

}
