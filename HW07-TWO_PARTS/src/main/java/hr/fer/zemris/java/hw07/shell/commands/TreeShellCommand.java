package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.utility.SplitUtility;
import hr.fer.zemris.java.hw07.visitor.MyVisitor;

/**
 * Represents command for writing structure of the files as tree.
 * 
 * @author Filip Karacic
 *
 */
public class TreeShellCommand implements ShellCommand {

	/**
	 * List of strings representing the description of this command.
	 */
	private static List<String> description;

	static {
		description = new ArrayList<>();

		description.add("The 'tree' command expects a single argument: directory name.");
		description.add("Prints a tree (each directory level shifts output two charatcers to the right).");
		
		description = Collections.unmodifiableList(description);
	}
	
	/**
	 * Performs tree command. Prints structure of the directory as a tree.
	 **/
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env, "Environment cannot be null.");
		Objects.requireNonNull(arguments, "Argument cannot be null");
		
		String[] commandArguments = SplitUtility.splitArguments(arguments, env);
		
		if(commandArguments == null || commandArguments.length != 2) {
			env.writeln("Invalid tree command. Was: " + arguments);
		}else {
			MyVisitor visitor = new MyVisitor(env);
			
			try {
				Files.walkFileTree(env.getCurrentDirectory().resolve(Paths.get(commandArguments[1])), visitor);
			} catch (Exception e) {
				env.writeln("Invalid path for the three command. Was: " + commandArguments[1]);
			}
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		return description;
	}

}
