package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.utility.SplitUtility;

/**
 * Represents copy command that creates copy of the given file as the given
 * target file.
 * 
 * @author Filip Karacic
 *
 */
public class CopyShellCommand implements ShellCommand {

	/**
	 * List of strings representing the description of this command.
	 */
	private static List<String> description;

	static {
		description = new ArrayList<>();

		description.add("The 'copy' command expects two arguments.");
		description.add("The first one is a source file name and the second one is a destination file name.");
		description.add("If destination file exists, user is asked if it is allowed to overwrite it.");
		description.add("Copy command works only with files (no directories).");
		description.add(
				"If the second argument is directory, it is assumed that user wants to copy the original file into that directory using the original file name.");
		description.add("Follows file creation date/time and finally file name.");

		description = Collections.unmodifiableList(description);
	}

	/**
	 * Performs copy command. Copies content of the given file to the target file.
	 **/
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env, "Environment cannot be null.");
		Objects.requireNonNull(arguments, "Argument cannot be null");

		String[] commandArguments = SplitUtility.splitArguments(arguments, env);

		if (commandArguments == null || commandArguments.length != 3) {
			env.writeln("Invalid number of arguments for copy command.");
			return ShellStatus.CONTINUE;
		}

		Path original;
		Path copy;

		try {
			original = env.getCurrentDirectory().resolve(Paths.get(commandArguments[1]));
			copy = env.getCurrentDirectory().resolve(Paths.get(commandArguments[2]));
		} catch (InvalidPathException e) {
			env.writeln("Invalid paths given.");
			return ShellStatus.CONTINUE;
		}

		return checkAndCopyFiles(original, copy, env);
	}

	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		return description;
	}

	/**
	 * Validates paths and copies original file to the target file.
	 * 
	 * @param original
	 *            original file
	 * @param copy
	 *            target file
	 * @param env
	 *            environment of the shell
	 * @return CONTINUE status of the shell
	 */
	private ShellStatus checkAndCopyFiles(Path original, Path copy, Environment env) {
		if (Files.isDirectory(original)) {
			env.writeln("First given argument is path to directory. Must be a file.");
			return ShellStatus.CONTINUE;
		}

		if (Files.isDirectory(copy)) {
			copy = copy.resolve(original.getFileName());
		}

		if (fileExists(copy, env) == ShellStatus.CONTINUE)
			return ShellStatus.CONTINUE;

		if (original.toAbsolutePath().normalize().equals(copy.toAbsolutePath().normalize())) {
			env.writeln("File not copied. Input and output files are equal.");
			return ShellStatus.CONTINUE;
		}

		return copyFiles(original, copy, env);
	}

	/**
	 * Returns CONTINUE shell status if there is no permition to overwrite file,
	 * <code>null</code> if the file does not exist or is permitted to overwrite.
	 * 
	 * @param copy
	 *            target file
	 * @param env
	 *            emvironment of the shell
	 * 
	 * @return CONTINUE shell status if there is no permition to overwrite file,
	 *         <code>null</code> if the file does not exist or is permitted to
	 *         overwrite
	 */
	private ShellStatus fileExists(Path copy, Environment env) {
		if (Files.exists(copy)) {
			while (true) {
				env.write("Given file already exists. Overwrite? [Y, N]: ");
				String answer = env.readLine();

				if (answer.equals("N")) {
					env.writeln("File not copied, no permition to overwrite.");
					return ShellStatus.CONTINUE;
				} else if (answer.equals("Y")) {
					break;
				} else {
					env.writeln("Invalid answer. Please provide [Y, N]");
				}
			}
		}

		return null;
	}

	/**
	 * Copies original file to the target file.
	 * 
	 * @param original
	 *            original file
	 * @param copy
	 *            target file
	 * @param env
	 *            environment of the shell
	 * @return shell status CONTINUE
	 * 
	 * @throws IllegalStateException
	 *             if error occurs while copying
	 */
	private ShellStatus copyFiles(Path original, Path copy, Environment env) {
		try (Stream<String> lines = Files.lines(original); Writer os = Files.newBufferedWriter(copy)) {

			lines.forEach(line -> {
				try {
					os.write(line + "\n");
				} catch (IOException e) {
					throw new IllegalStateException("Cannot write to the copy file.");
				}
			});

			env.writeln("File " + original.getFileName() + " copied to " + copy.getFileName());
		} catch (Exception e) {
			env.writeln("Cannot copy the given files. Error occured.");
		}

		return ShellStatus.CONTINUE;
	}
}
