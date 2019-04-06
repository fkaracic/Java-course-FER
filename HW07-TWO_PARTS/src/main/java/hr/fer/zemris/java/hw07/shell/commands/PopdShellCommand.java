package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.InvalidPathException;
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
 * Represents command that pops current directory from the stack(if there is
 * one) and sets current directory to the popped one.
 * 
 * @author Filip Karacic
 * 
 */
public class PopdShellCommand implements ShellCommand {

	/**
	 * List of strings representing the description of this command.
	 */
	private static List<String> description;

	static {
		description = new ArrayList<>();

		description.add("Command 'popd' takes no argument.");
		description.add("Command pops current directory from the stack (if there is one).");
		description.add("Sets current directory to the popped directory.");
		description.add("If the poped directory does not exist (i.e. was deleted in the meantime) ");
		description.add("it will be poped but the current directory will not be changed.");
		description = Collections.unmodifiableList(description);
	}

	/**
	 * Performs popd command. Pops directory from the stack and sets the current
	 * directory to the popped one.
	 **/
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env, "Environment cannot be null.");
		Objects.requireNonNull(arguments, "Argument cannot be null");

		if (!arguments.equals("popd")) {
			env.writeln("Invalid popd command.");
		} else {
			try {
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
						env.setCurrentDirectory(dir);
						env.writeln("Current directory set to: " + dir);
					}
				}

			} catch (InvalidPathException e) {
				env.writeln("The popped path is not exisiting directory.");
			}
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "popd";
	}

	@Override
	public List<String> getCommandDescription() {
		return description;
	}

}
