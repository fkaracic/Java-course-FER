package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Represents pwd command that is used to print absolute path of the current
 * directory.
 * 
 * @author Filip Karacic
 *
 */
public class PwdShellCommand implements ShellCommand {

	/**
	 * List of strings representing the description of this command.
	 */
	private static List<String> description;

	static {
		description = new ArrayList<>();

		description.add("Command 'pwd' takes no arguments.");
		description.add("Prints absoulte path of current directory.");

		description = Collections.unmodifiableList(description);
	}

	/**
	 * Performs pwd command. Prints absolute path of the current directory.
	 **/
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env, "Environment cannot be null.");
		Objects.requireNonNull(arguments, "Argument cannot be null");

		if (arguments.equals("pwd")) {
			env.writeln("Current directory: " + env.getCurrentDirectory());
		} else {
			env.writeln("Invalid pwd command. Was: " + arguments);
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "pwd";
	}

	@Override
	public List<String> getCommandDescription() {
		return description;
	}

}
