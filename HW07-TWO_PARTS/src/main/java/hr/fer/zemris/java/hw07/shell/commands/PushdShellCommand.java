package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.utility.SplitUtility;

/**
 * Represents pushd command that pushes current directory to the stack and sets
 * current directory to the given directory.
 * 
 * @author Filip Karacic
 *
 */
public class PushdShellCommand implements ShellCommand {

	/**
	 * List of strings representing the description of this command.
	 */
	private static List<String> description;

	static {
		description = new ArrayList<>();

		description.add("Command 'pushd' takes one argument.");
		description.add("The given argument must be path to existing directory.");
		description.add("Command pushes current directory to the stack and sets current directory to the given.");

		description = Collections.unmodifiableList(description);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env, "Environment cannot be null.");
		Objects.requireNonNull(arguments, "Argument cannot be null");

		String[] commandArguments = SplitUtility.splitArguments(arguments, env);

		if (commandArguments == null || commandArguments.length != 2) {
			env.writeln("Invalid number of arguments for pushd command. Must provide only existing directory path.");
		} else {
			try {
				Path dir = env.getCurrentDirectory().resolve(Paths.get(commandArguments[1]));

				Path previous = env.getCurrentDirectory();
				env.setCurrentDirectory(dir);

				Object value = env.getSharedData("cdstack");

				if (value == null) {
					Stack<Path> stack = new Stack<>();

					stack.push(previous);
					env.setSharedData("cdstack", stack);
				} else {
					@SuppressWarnings("unchecked")
					Stack<Path> stack = (Stack<Path>) value;

					stack.push(previous);
					env.setSharedData("cdstack", stack);
				}

				env.writeln(
						"Directory " + previous + " pushed to stack and the current directory is set to " + dir + ".");

			} catch (IllegalArgumentException e) {
				env.writeln("The given path is not exisiting directory.");
			}
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "pushd";
	}

	@Override
	public List<String> getCommandDescription() {
		return description;
	}

}
