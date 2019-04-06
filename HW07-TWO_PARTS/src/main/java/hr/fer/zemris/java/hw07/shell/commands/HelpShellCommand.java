package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Represents help command of this shell that provides information about
 * available commands and the description of each command.
 * 
 * @author Filip Karacic
 *
 */
public class HelpShellCommand implements ShellCommand {

	/**
	 * List of strings representing the description of this command.
	 */
	private static List<String> description;

	static {
		description = new ArrayList<>();

		description.add("Command 'help' takes zero or one argument.");
		description.add("If started with no arguments, it lists names of all supported commands.");
		description.add("If started with single argument, it prints name and the description of selected command.");
		description.add("Prints error message if no such command exists.");

		description = Collections.unmodifiableList(description);
	}

	/**
	 * Performs help command. Provides info about available commands and their
	 * description.
	 **/
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env, "Environment cannot be null.");
		Objects.requireNonNull(arguments, "Argument cannot be null");

		if (arguments.equals("help")) {
			env.writeln("Available commands:");
			env.commands().forEach((key, value) -> env.writeln(value.getCommandName()));
		} else {
			String[] helpArguments = arguments.split("\\s+");
			ShellCommand command;

			if (helpArguments.length == 2 && (command = env.commands().get(helpArguments[1])) != null) {
				env.writeln("Command name: " + command.getCommandName());
				command.getCommandDescription().forEach(s -> env.writeln(s));
			} else {
				env.writeln("Invalid help command. Was: " + arguments);
			}
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		return description;
	}

}
