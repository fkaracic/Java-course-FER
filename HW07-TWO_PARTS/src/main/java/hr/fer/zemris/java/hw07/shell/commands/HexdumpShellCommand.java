package hr.fer.zemris.java.hw07.shell.commands;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.utility.SplitUtility;

/**
 * Represents hexdump command that is used for conversion of text from the given
 * file to hexadecimal output.
 * 
 * @author Filip Karacic
 *
 */
public class HexdumpShellCommand implements ShellCommand {

	/**
	 * List of strings representing the description of this command.
	 */
	private static List<String> description;

	static {
		description = new ArrayList<>();

		description.add("The 'hexdump' command expects a single argument: file name.");
		description.add("Produces hex-output.");

		description = Collections.unmodifiableList(description);
	}

	/**
	 * Performs hexdump command. Prints content of the given file in hexadecimal
	 * characters.
	 **/
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env, "Environment cannot be null.");
		Objects.requireNonNull(arguments, "Argument cannot be null");

		String[] commandArguments = SplitUtility.splitArguments(arguments, env);

		if (commandArguments != null && commandArguments.length == 2) {
			try {
				Path path = env.getCurrentDirectory().resolve(Paths.get(commandArguments[1]));

				readAndProduceHex(path, env);
			} catch (InvalidPathException e) {
				env.writeln("Invalid path given. Was: " + commandArguments[1]);
				return ShellStatus.CONTINUE;
			} catch (Exception e) {
				env.writeln("Error producing hex from the given file.");
				return ShellStatus.CONTINUE;
			}
		} else {
			env.writeln("Invalid arguments for hexdump command");
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		return description;
	}

	/**
	 * Reads bytes from the given file and produces output for every 16 bytes as a
	 * one line.
	 * 
	 * @param path
	 *            path to the file to be read
	 * @param env
	 *            environment of the shell
	 * @throws IOException
	 *             if error while reading occurs
	 */
	private void readAndProduceHex(Path path, Environment env) throws IOException {
		try (BufferedInputStream is = new BufferedInputStream(Files.newInputStream(path))) {
			byte[] buff = new byte[16];
			int lineNumber = 0;

			while (true) {

				int r = is.read(buff, 0, buff.length);

				if (r < 1)
					break;

				env.writeln(produceOutput(lineNumber, r, buff));

				lineNumber++;
			}
		}

	}

	/**
	 * Produces output for one line.
	 *
	 * @param lineNumber
	 *            ordinal number of the line
	 * @param r
	 *            number of bytes written
	 * @param buff
	 *            array of bytes
	 * 
	 * @return String output
	 */
	private String produceOutput(int lineNumber, int r, byte[] buff) {

		StringBuilder output = new StringBuilder();

		output.append(String.format("%07d0: ", lineNumber));

		for (int j = 0; j < r; j++) {
			output.append(String.format(j == 7 ? "%02X|" : "%02X ", buff[j]));
		}

		if (r < 16) {
			for (int j = r; j < 16; j++)
				output.append(j == 7 ? "  |" : "   ");
		}

		byte[] bytes = replaceInvalidBytes(buff, r);

		String novi;

		try {
			novi = new String(bytes, "UTF-8");
			output.append("| " + novi);
		} catch (UnsupportedEncodingException e) {
		}

		return output.toString();
	}

	/**
	 * Replaces invalid bytes (less than 32 and greater than 107) with the 46.
	 * 
	 * @param buff
	 *            array of bytes
	 * @param r
	 *            size of bytes written
	 * @return array of bytes with invalid bytes replaced
	 */
	private static byte[] replaceInvalidBytes(byte[] buff, int r) {
		byte[] result = new byte[buff.length];

		for (int i = 0; i < r; i++) {
			if (buff[i] < 32 || buff[i] > 127) {
				result[i] = 46;
			} else {
				result[i] = buff[i];
			}
		}

		return result;
	}

}
