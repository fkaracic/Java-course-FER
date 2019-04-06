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
 * Represents dropd command that pops last added directory to the stack (if
 * there is one) and disregards it.
 * 
 * @author Filip Karacic
 *
 */
public class DropdShellCommand implements ShellCommand {

	/**
	 * List of strings representing the description of this command.
	 */
	private static List<String> description;

	static {
		description = new ArrayList<>();

		description.add("Command 'dropd' takes no argument.");
		description.add("Command pops current directory from the stack (if there is one).");
		description.add("Disregards poped directory.");

		description = Collections.unmodifiableList(description);
	}

	/**
	 * Performs dropd command. Removes the last added directory to
	 * the stack (if there is one) and disregards it.
	 **/
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env, "Environment cannot be null.");
		Objects.requireNonNull(arguments, "Argument cannot be null");

		if (!arguments.equals("dropd")) {
			env.writeln("Invalid dropd command.");
		} else {
			Object value = env.getSharedData("cdstack");

			if (value == null) {
				env.writeln("There are no directories on the stack.");
			} else {
				@SuppressWarnings("unchecked")
				Stack<Path> stack = (Stack<Path>) value;

				if (stack.isEmpty()) {
					env.writeln("There are no directories on the stack.");
				} else {
					Path dir = stack.pop();
					env.writeln("Directory " + dir + " popped and disregared.");
				}
			}
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "dropd";
	}

	@Override
	public List<String> getCommandDescription() {
		return description;
	}

}
