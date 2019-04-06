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
import hr.fer.zemris.java.hw07.visitor.MyRemoveVisitor;

/**
 * Represents rmtree command that is used to remove directory and all of its
 * content.
 * 
 * @author Filip Karacic
 *
 */
public class RmtreeShellCommand implements ShellCommand {

	/**
	 * List of strings representing the description of this command.
	 */
	private static List<String> description;

	static {
		description = new ArrayList<>();

		description.add("Command 'rmtree' takes one argument.");
		description.add("The given argument must be path to existing directory.");
		description.add("Command deletes current directory and all of its content.");

		description = Collections.unmodifiableList(description);
	}

	/**
	 * Performs rmtree command. Removes the given directory and all of its content.
	 **/
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env, "Environment cannot be null.");
		Objects.requireNonNull(arguments, "Argument cannot be null");

		String[] commandArguments = SplitUtility.splitArguments(arguments, env);

		if (commandArguments == null || commandArguments.length != 2) {
			env.writeln("Invalid rmtree command. Was: " + arguments);
		} else {
			FileVisitor<Path> visitor = new MyRemoveVisitor();

			try {
				Path dir = env.getCurrentDirectory().resolve(Paths.get(commandArguments[1]));

				Files.walkFileTree(dir, visitor);
				env.writeln("Directory " + dir + " and all of its content removed.");

			} catch (InvalidPathException e) {
				env.writeln("Invalid path for the rmthree command. Was: " + commandArguments[1]);
			} catch (IOException e) {
				env.writeln("Error removing given directory.");
			}
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "rmtree";
	}

	@Override
	public List<String> getCommandDescription() {
		return description;
	}

}
