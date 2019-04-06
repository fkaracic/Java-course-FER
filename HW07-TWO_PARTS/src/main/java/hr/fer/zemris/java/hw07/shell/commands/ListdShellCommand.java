package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Represents ls command that lists all directories on the stack if there is
 * some.
 * 
 * @author Filip Karacic
 *
 */
public class ListdShellCommand implements ShellCommand {

	/**
	 * List of strings representing the description of this command.
	 */
	private static List<String> description;

	static {
		description = new ArrayList<>();

		description.add("Command 'listd' takes no argument.");
		description.add("Command prints all directories from the stack (if there are some).");

		description = Collections.unmodifiableList(description);
	}

	/**
	 * Performs listd command. Prints all the directories on the stack (if there is
	 * some).
	 **/
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env, "Environment cannot be null.");
		Objects.requireNonNull(arguments, "Argument cannot be null");

		if (!arguments.equals("listd")) {
			env.writeln("Invalid listd command. Was: " + arguments);
			return ShellStatus.CONTINUE;
		}

		Object value = env.getSharedData("cdstack");

		if (value == null) {
			env.writeln("There are no stored directories.");
		} else {
			@SuppressWarnings("unchecked")
			Stack<Path> stack = (Stack<Path>) value;

			if (stack.isEmpty()) {
				env.writeln("There are no stored directories.");
			} else {
				env.writeln("Directories on the stack:");

				for (Path path : stack) {
					env.writeln(path.toString());
				}

			}

		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "listd";
	}

	@Override
	public List<String> getCommandDescription() {
		return description;
	}

}
