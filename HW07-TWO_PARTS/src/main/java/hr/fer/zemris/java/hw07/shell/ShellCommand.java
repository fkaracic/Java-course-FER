package hr.fer.zemris.java.hw07.shell;

import java.util.List;

/**
 * Interface {@code ShellCommand} is used to describe commands of the shell. It
 * provides information about the commands name, description of the command and
 * performs execution of the command.
 * 
 * @author Filip Karacic
 *
 */
public interface ShellCommand {

	/**
	 * Performs execution of this command. Command communicates with user through
	 * the {@code env} argument.
	 * 
	 * @param env
	 *            environment of the shell
	 * @param arguments
	 *            arguments of this command as {@code String}
	 * @return status after the execution of the given command.
	 */
	ShellStatus executeCommand(Environment env, String arguments);

	/**
	 * Returns name of this command.
	 * 
	 * @return command name
	 */
	String getCommandName();

	/**
	 * Returns list containing {@code String} lines with command description.
	 * 
	 * @return list containing {@code String} lines with command description.
	 */
	List<String> getCommandDescription();

}
