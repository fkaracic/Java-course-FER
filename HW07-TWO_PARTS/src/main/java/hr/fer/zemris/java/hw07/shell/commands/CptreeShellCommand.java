package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitor;
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
import hr.fer.zemris.java.hw07.visitor.MyCopyVisitor;

/**
 * Represents command that copies structure of the given directory to the
 * destination given. All directories and files from the given directory are
 * being copied.
 *
 */
public class CptreeShellCommand implements ShellCommand {

	/**
	 * List of strings representing the description of this command.
	 */
	private static List<String> description;

	static {
		description = new ArrayList<>();

		description.add("Command 'cptree' takes two arguments.");
		description.add("The first argument must be path to existing directory.");
		description.add("The second argument must be path to existing directory ");
		description.add("or to the non-existing directory whose parent directory is existing.");
		description.add("Command coppies directory given as the first argument and all of its content ");
		description.add("to the second given argument representing directory.");

		description = Collections.unmodifiableList(description);
	}

	/**
	 * Performs cptree command. Copies content of the given directory to the
	 * destination directory.
	 **/
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env, "Environment cannot be null.");
		Objects.requireNonNull(arguments, "Argument cannot be null");

		String[] commandArguments = SplitUtility.splitArguments(arguments, env);

		if (commandArguments == null || commandArguments.length != 3) {
			env.writeln("Invalid cptree command. Was: " + arguments);
		} else {

			try {
				Path original = env.getCurrentDirectory().resolve(Paths.get(commandArguments[1]));
				Path copy = env.getCurrentDirectory().resolve(Paths.get(commandArguments[2]));

				if (!original.toFile().isDirectory() || !original.toFile().exists()) {
					env.writeln("The first argument must be existing directory.");
					return ShellStatus.CONTINUE;
				}

				if (copy.toFile().exists()) {
					copy = copy.resolve(original.getFileName());
				} else {
					if (!copy.getParent().toFile().exists()) {
						env.writeln("The second argument must be directory.");
						return ShellStatus.CONTINUE;
					}
				}

				FileVisitor<Path> visitor = new MyCopyVisitor(original, copy);

				Files.walkFileTree(original, visitor);

				env.writeln("Files succesfully copied.");

			} catch (InvalidPathException e) {
				env.writeln("Invalid path.");
			} catch (IOException e) {
				env.writeln("Error copying files.");
			}
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cptree";
	}

	@Override
	public List<String> getCommandDescription() {
		return description;
	}

}
