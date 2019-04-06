package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.utility.SplitUtility;

/**
 * Represents cat command that reads file with specified charset and prints it.
 * If specified charset is not given, default charset for user's system is used.
 * 
 * @author Filip Karacic
 *
 */
public class CatShellCommand implements ShellCommand {

	/**
	 * List of strings representing the description of this command.
	 */
	private static List<String> description;

	static {
		description = new ArrayList<>();

		description.add("Command 'cat' takes one or two arguments.");
		description.add("The first argument is path to some file and is mandatory.");
		description.add("The second argument is charset name that should be used to interpret chars from bytes.");
		description.add("If not provided, a default platform charset is used.");
		description.add("This command opens the given file and writes its content to console.");

		description = Collections.unmodifiableList(description);
	}

	/**
	 * Performs cat command. Prints content of the given file.
	 **/
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env, "Environment cannot be null.");
		Objects.requireNonNull(arguments, "Argument cannot be null");

		String[] commandArguments = SplitUtility.splitArguments(arguments, env);

		Charset charset;
		if (commandArguments != null && commandArguments.length == 2) {
			charset = Charset.defaultCharset();
		} else if (commandArguments != null && commandArguments.length == 3) {
			try {
				charset = Charset.forName(commandArguments[2]);
			} catch (IllegalCharsetNameException e) {
				env.writeln("Invalid charset. Was: '" + commandArguments[2] + "'.");
				return ShellStatus.CONTINUE;
			}
		} else {
			env.writeln("Invalid number of arguments for cat command.");
			return ShellStatus.CONTINUE;
		}

		try (Stream<String> lines = Files.lines(env.getCurrentDirectory().resolve(Paths.get(commandArguments[1])),
				charset)) {
			env.writeln("Content of the file : ");
			lines.forEach(line -> env.writeln(line));

		} catch (Exception e) {
			env.writeln("Error within the given file. Path was " + commandArguments[1]);
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		return description;
	}

}
