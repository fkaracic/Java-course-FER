package hr.fer.zemris.java.hw07.shell.commands;

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
 * Represents cd command that is used to change working directory for the shell.
 * 
 * @author Filip Karacic
 *
 */
public class CdShellCommand implements ShellCommand {

	/**
	 * List of strings representing the description of this command.
	 */
	private static List<String> description;

	static {
		description = new ArrayList<>();

		description.add("Command 'cd' takes one arguments.");
		description.add("The given argument should be new directory.");

		description = Collections.unmodifiableList(description);
	}

	/**
	 * Performs cd command. Changes current directory to the given directory.
	 **/
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env, "Environment cannot be null.");
		Objects.requireNonNull(arguments, "Argument cannot be null");

		String[] commandArguments = SplitUtility.splitArguments(arguments, env);

		if (commandArguments.length != 2) {
			env.writeln("cd command requires one argument: directory path.");
		} else {
			try {
				Path dir = env.getCurrentDirectory().resolve(Paths.get(commandArguments[1]));

				Path previous = env.getCurrentDirectory();
				env.setCurrentDirectory(dir);

				env.writeln("Current directory changed from: " + previous + " to: " + env.getCurrentDirectory());
			} catch (InvalidPathException e) {
				env.writeln("The given path is not exisiting directory.");
			} catch(IllegalArgumentException e) {
				env.writeln("The given path is not exisiting directory.");
			}
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cd";
	}

	@Override
	public List<String> getCommandDescription() {
		return description;
	}

}
