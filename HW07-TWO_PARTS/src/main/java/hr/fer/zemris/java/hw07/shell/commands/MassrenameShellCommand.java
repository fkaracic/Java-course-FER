package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.utility.SplitUtility;
import hr.fer.zemris.java.namebuilder.NameBuilder;
import hr.fer.zemris.java.namebuilder.NameBuilderInfo;
import hr.fer.zemris.java.namebuilder.NameBuilderParser;

/**
 * Represents massrename command that is used for renaming/moving of the
 * files(not directories).
 * <p>
 * 
 * Command has this form: 'massrename DIR1 DIR2 CMD MASK REST'.
 * 
 * @author Filip Karacic
 *
 */
public class MassrenameShellCommand implements ShellCommand {

	/**
	 * List of strings representing the description of this command.
	 */
	private static List<String> description;

	static {
		description = new ArrayList<>();

		description.add("Command 'massrename' is used for renaming/moving of the files (not directories).");
		description.add("Command has this form: 'massrename DIR1 DIR2 CMD MASK REST'.");
		description.add("Files are transfered from the directory DIR1 to directory DIR2.");
		description.add("The first argument is path to the directory containing files.");
		description.add("The second argument is path to the destination directory.");
		description.add("CMD argument can be: filter, groups, show or execute.");
		description.add("Mask is regular expression that selects files from DIR1 to be processed.");
		description.add("If CMD is 'filter': command prints name of the files selected by the mask.");
		description.add("If CMD is 'groups': command prints groups defined by the mask for all of the selected files.");
		description.add(
				"If CMD is 'show': argument representing expression that will define new name generating must be provided.");
		description.add("Command then prints selected names and new names.");
		description.add(
				"If CMD is 'execute': command executes renaming/moving from DIR1 to DIR2, where DIR1 and DIR2 can be the same directories");

		description = Collections.unmodifiableList(description);
	}

	/**
	 * Performs massrename command. Renames/replaces files from the given directory
	 * to the destination directory which can be the same.
	 **/
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env, "Environment cannot be null.");
		Objects.requireNonNull(arguments, "Argument cannot be null");

		String[] commandArguments = SplitUtility.splitArguments(arguments, env);

		if (commandArguments == null || (commandArguments.length != 5 && commandArguments.length != 6)) {
			env.writeln("Wrong number of arguments for massrename command.");
		} else {
			CMDCommand subCommand = extractCMD(commandArguments);

			if (subCommand == CMDCommand.UNKNOWN) {
				env.writeln("Invalid massrename command. Was: " + arguments + ".");
				return ShellStatus.CONTINUE;
			}

			performCommand(commandArguments, subCommand, env);
		}

		return ShellStatus.CONTINUE;

	}

	@Override
	public String getCommandName() {
		return "massrename";
	}

	@Override
	public List<String> getCommandDescription() {
		return description;
	}

	/**
	 * Validates given arguments and performs massrename command.
	 * 
	 * @param commandArguments
	 *            arguments of the command
	 * @param subCommand
	 *            CMD (sub-command)
	 * @param env
	 *            environment of the shell
	 */
	private void performCommand(String[] commandArguments, CMDCommand subCommand, Environment env) {
		try {
			Path dir1 = env.getCurrentDirectory().resolve(Paths.get(commandArguments[1]));
			Path dir2 = env.getCurrentDirectory().resolve(Paths.get(commandArguments[2]));

			File[] children = dir1.toFile().listFiles();

			if (children == null) {
				env.writeln("No files for the path given as DIR1.");
				return;
			}

			Pattern pattern = Pattern.compile(commandArguments[4]);

			if (commandArguments.length == 5) {
				filterOrGroup(subCommand, env, children, pattern);
			} else {
				showOrExecute(subCommand, env, children, pattern, dir2, commandArguments);
			}
		} catch (InvalidPathException e) {
			env.writeln("Invalid paths of the directories given.");
		} catch (IOException e) {
			env.writeln("Error moving files.");
		} catch (PatternSyntaxException e) {
			env.writeln("Invalid pattern given.");
			return;
		}
	}

	/**
	 * Performs action for show or execute CMD.
	 * 
	 * @param subCommand
	 *            CMD (sub-command)
	 * @param env
	 *            environment of the shell
	 * @param children
	 *            children (files of the given directory)
	 * @param pattern
	 *            pattern for files filtering
	 * @param dir2
	 *            target directory
	 * @param commandArguments
	 *            arguments of the command
	 * 
	 * @throws IOException
	 *             if error ocurrs while moving files
	 */
	private void showOrExecute(CMDCommand subCommand, Environment env, File[] children, Pattern pattern, Path dir2,
			String[] commandArguments) throws IOException {
		NameBuilderParser parser = new NameBuilderParser(commandArguments[5]);
		NameBuilder builder = parser.getNameBuilder();

		for (File child : children) {
			Matcher matcher = pattern.matcher(child.getName());
			if (!matcher.matches())
				continue;

			NameBuilderInfo info = createInfo(matcher);
			try {
				builder.execute(info);

				String newName = info.getStringBuilder().toString();

				if (subCommand == CMDCommand.EXECUTE) {
					Files.move(child.toPath(), dir2.resolve(newName), StandardCopyOption.REPLACE_EXISTING);
					env.writeln(child.toPath() + " => " + dir2.resolve(newName));
				} else {
					env.writeln(child.getName() + " => " + newName);
				}

			} catch (IndexOutOfBoundsException e) {
				env.writeln("Invalid command: " + e.getMessage());
				return;
			}
		}

	}

	/**
	 * Performs action for filter or group CMD.
	 * 
	 * @param subCommand
	 *            CMD (sub-command)
	 * @param env
	 *            environment of the shell
	 * @param children
	 *            children (files of the given directory)
	 * @param pattern
	 *            pattern for files filtering
	 */
	private void filterOrGroup(CMDCommand subCommand, Environment env, File[] children, Pattern pattern) {
		for (File child : children) {
			Matcher matcher = pattern.matcher(child.getName());
			if (!matcher.matches())
				continue;

			env.write(child.getName() + " ");

			if (subCommand == CMDCommand.GROUPS) {
				groups(env, matcher);
			} else {
				env.writeln("");
			}
		}

	}

	/**
	 * Creates name builder info provider.
	 * 
	 * @param matcher
	 *            matcher from the given pattern
	 * 
	 * @return name builder info provider object
	 */
	private NameBuilderInfo createInfo(Matcher matcher) {
		return new NameBuilderInfo() {
			StringBuilder builder = new StringBuilder();

			@Override
			public StringBuilder getStringBuilder() {
				return builder;
			}

			@Override
			public String getGroup(int index) {
				return matcher.group(index);
			}

		};
	}

	/**
	 * Validates CMD (sub-command) and returns {@code CMDCommand} object
	 * representing type of the command.
	 * 
	 * @param commandArguments
	 *            arguments for this command
	 * 
	 * @return {@code CMDCommand} object representing type of the command.
	 */
	private CMDCommand extractCMD(String[] commandArguments) {
		switch (commandArguments[3]) {
		case "filter":
			if (commandArguments.length == 5) {
				return CMDCommand.FILTER;
			}
			return CMDCommand.UNKNOWN;
		case "groups":
			if (commandArguments.length == 5) {
				return CMDCommand.GROUPS;
			}
			return CMDCommand.UNKNOWN;
		case "show":
			if (commandArguments.length == 6) {
				return CMDCommand.SHOW;
			}
			return CMDCommand.UNKNOWN;
		case "execute":
			if (commandArguments.length == 6) {
				return CMDCommand.EXECUTE;
			}
			return CMDCommand.UNKNOWN;
		default:
			return CMDCommand.UNKNOWN;
		}
	}

	/**
	 * Prints groups according to the given matcher for the file given.
	 * 
	 * @param env
	 *            environment of the shell
	 * @param matcher
	 *            matcher from the given pattern
	 *
	 */
	private void groups(Environment env, Matcher matcher) {
		int count = matcher.groupCount();

		for (int i = 0; i <= count; i++) {
			env.write(i + ": " + matcher.group(i) + " ");
		}

		env.writeln("");
	}

	/**
	 * Represent CMD sub-command type.
	 *
	 */
	private static enum CMDCommand {
		/**
		 * Filter sub-command.
		 */
		FILTER,

		/**
		 * Groups sub-command.
		 */
		GROUPS,

		/**
		 * Show sub-command.
		 */
		SHOW,

		/**
		 * Execute sub-command
		 */
		EXECUTE,

		/**
		 * Unknown sub-command.
		 */
		UNKNOWN;
	}

}
