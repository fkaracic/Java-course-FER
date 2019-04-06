package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Files;
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
 * Represents command for creating directories.
 * 
 * @author Filip Karacic
 *
 */
public class MkdirShellCommand implements ShellCommand {

	/**
	 * List of strings representing the description of this command.
	 */
	private static List<String> description;

	static {
		description = new ArrayList<>();

		description.add("The 'mkdir' command takes a single argument: directory name.");
		description.add("Creates the appropriate directory structure.");

		description = Collections.unmodifiableList(description);
	}

	/**
	 * Performs mkdir command. Creates the given directory.
	 **/
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env, "Environment cannot be null.");
		Objects.requireNonNull(arguments, "Argument cannot be null");

		String[] commandArguments = SplitUtility.splitArguments(arguments, env);

		if (commandArguments == null || commandArguments.length != 2) {
			env.writeln("Invalid mkdir command. Was: " + arguments);
			return ShellStatus.CONTINUE;
		}
		
		try {
			Path dir = env.getCurrentDirectory().resolve(Paths.get(commandArguments[1]));
			Files.createDirectories(dir);
			
			env.writeln("Directory structure created. Structure: " + dir.normalize());
			return ShellStatus.CONTINUE;
		}catch(Exception e) {
			env.writeln("Error while creating directories.");
			return ShellStatus.CONTINUE;
		}
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		return description;
	}

}
