package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Represents command that prints all charsets for this system.
 * 
 * @author Filip Karacic
 *
 */
public class CharsetsShellCommand implements ShellCommand {
	/**
	 * List of strings representing the description of this command.
	 */
	private static List<String> description;

	static {
		description = new ArrayList<>();

		description.add("Command 'charsets' takes no arguments.");
		description.add("Lists names of supported charsets for this Java platform.");
		description.add("A single charset name is written per line.");

		description = Collections.unmodifiableList(description);
	}

	/**
	 * Performs charsets command. Prints all of the charsets defined for users
	 * system.
	 **/
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env, "Environment cannot be null.");
		Objects.requireNonNull(arguments, "Argument cannot be null");

		if (!arguments.equals("charsets")) {
			env.writeln("Invalid charsets command. Was: " + arguments);
			return ShellStatus.CONTINUE;
		}

		env.writeln("Supported charsets for this Java platform:");
		Charset.availableCharsets().forEach((key, value) -> env.writeln(value.toString()));
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "charsets";
	}

	@Override
	public List<String> getCommandDescription() {
		return description;
	}

}
