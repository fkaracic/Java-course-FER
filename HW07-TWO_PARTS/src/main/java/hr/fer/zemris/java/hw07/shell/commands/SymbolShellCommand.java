package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Represents symbol command for retrieving or changing symbols of the shell.
 * 
 * @author Filip Karacic
 *
 */
public class SymbolShellCommand implements ShellCommand {

	/**
	 * List of strings representing the description of this command.
	 */
	private static List<String> description;

	static {
		description = new ArrayList<>();

		description.add("Command 'symbol' takes one or two arguments.");
		description.add("The first argument is a name of the symbol.");
		description.add("The second argument is a character that should replace current character for that symbol.");
		description.add("If the second argument is not provided, current character for that symbol is printed.");
		description.add(
				"If the second argument is provided, replaces current character with the given character for that symbol.");

		description = Collections.unmodifiableList(description);
	}

	/**
	 * Performs symbol command. Prints/changes current PROMPT, MULTILINE or
	 * MORELINES symbol depending of arguments given.
	 **/
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env, "Environment cannot be null.");
		Objects.requireNonNull(arguments, "Argument cannot be null");

		String[] args = arguments.split("\\s+");

		if (args.length == 2 || args.length == 3) {
			if (args.length == 3 && args[2].length() != 1) {
				env.writeln("Invalid symbol");
				return ShellStatus.CONTINUE;
			}

			switch (args[1].toUpperCase()) {
			case "PROMPT":
				promptSymbol(args, env);
				break;
			case "MORELINES":
				moreLinesSymbol(args, env);
				break;
			case "MULTILINE":
				multiLineSymbol(args, env);
				break;
			default:
				env.writeln("Invalid symbol name.");
			}

		} else {
			env.writeln("Invalid symbol operation. Was: " + arguments);
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		return description;
	}

	/**
	 * Performs multiline command.
	 * 
	 * @param args
	 *            arguments of the command
	 * @param env
	 *            environment of the shell
	 */
	private void multiLineSymbol(String[] args, Environment env) {
		if (args.length == 2) {
			env.writeln("Symbol for MULTILINE is '" + env.getMultilineSymbol() + "'");
		} else {
			env.writeln("Symbol for MULTILINE changed from '" + env.getMultilineSymbol() + "' to: '" + args[2].charAt(0)
					+ "'");
			env.setMultilineSymbol(args[2].charAt(0));
		}
	}

	/**
	 * Performs moreline command.
	 * 
	 * @param args
	 *            arguments of the command
	 * @param env
	 *            environment of the shell
	 */
	private void moreLinesSymbol(String[] args, Environment env) {
		if (args.length == 2) {
			env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol() + "'");
		} else {
			env.writeln("Symbol for MORELINES changed from: '" + env.getMorelinesSymbol() + "' to: '"
					+ args[2].charAt(0) + "'");
			env.setMorelinesSymbol(args[2].charAt(0));
		}
	}

	/**
	 * Performs prompt command.
	 * 
	 * @param args
	 *            arguments of the command
	 * @param env
	 *            environment of the shell
	 */
	private void promptSymbol(String[] args, Environment env) {
		if (args.length == 2) {
			env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol() + "'");
		} else {
			env.writeln(
					"Symbol for PROMPT changed from '" + env.getPromptSymbol() + "' to: '" + args[2].charAt(0) + "'");
			env.setPromptSymbol(args[2].charAt(0));
		}

	}
}
