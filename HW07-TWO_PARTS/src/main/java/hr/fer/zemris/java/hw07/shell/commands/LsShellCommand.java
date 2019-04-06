package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.utility.SplitUtility;

/**
 * Represents command used for listing files of the given directory and
 * description of them. Provides information about type of the file,
 * readability, writability and executability. Also provides information about
 * size and date of last modification.
 * 
 * @author Filip Karacic
 *
 */
public class LsShellCommand implements ShellCommand {

	/**
	 * List of strings representing the description of this command.
	 */
	private static List<String> description;

	static {
		description = new ArrayList<>();

		description.add("Command 'ls' takes a single argument: directory.");
		description.add("Writes a directory listing.");
		description.add("The output consists of 4 columns.");
		description.add(
				"First column indicates if current object is directory (d), readable (r), writable (w) and executable (x).");
		description
				.add("Second column contains object size in bytes that is right aligned and occupies 10 characters.");
		description.add("Follows file creation date/time and finally file name.");

		description = Collections.unmodifiableList(description);
	}

	/**
	 * Performs ls command. Prints information about all of the files from the given
	 * directory (type, size, date of modification, name).
	 **/
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env, "Environment cannot be null.");
		Objects.requireNonNull(arguments, "Argument cannot be null");

		String[] commandArguments = SplitUtility.splitArguments(arguments, env);

		if (commandArguments == null || commandArguments.length != 2) {
			env.writeln("Invalid ls command. Was: " + arguments);
			return ShellStatus.CONTINUE;
		}

		try {
			Path dir = env.getCurrentDirectory().resolve(Paths.get(commandArguments[1]));
			File[] children = dir.toFile().listFiles();

			if (children == null) {
				env.writeln("No files for the given directory.");
				return ShellStatus.CONTINUE;
			}

			listChildren(children, env);

		} catch (InvalidPathException e) {
			env.writeln("Invalid path of the file. Was: " + commandArguments[1]);
		} catch (Exception e) {
			env.writeln("Error while listing files.");
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		return description;
	}

	/**
	 * List children of the given directory and produces output for each of them
	 * 
	 * @param children
	 *            children (files of the given directory)
	 * @param env
	 *            environment of the shell.
	 */
	private void listChildren(File[] children, Environment env) {
		for (File child : children) {
			String output = stringFromFile(child);

			if (output == null) {
				env.writeln("Error while listing files.");
				break;
			}

			env.writeln(output);
		}

	}

	/**
	 * Returns string created from path of the file.
	 * 
	 * @param child
	 *            file
	 * @return string from path of the file.
	 */
	private String stringFromFile(File child) {
		StringBuilder builder = new StringBuilder();

		builder.append(child.isDirectory() ? "d" : "-");
		builder.append(child.canRead() ? "r" : "-");
		builder.append(child.canWrite() ? "w" : "-");
		builder.append(child.canExecute() ? "x" : "-");

		builder.append(String.format("%10s", child.length()));

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BasicFileAttributeView faView = Files.getFileAttributeView(child.toPath(), BasicFileAttributeView.class,
				LinkOption.NOFOLLOW_LINKS);
		BasicFileAttributes attributes;

		try {
			attributes = faView.readAttributes();
		} catch (IOException e) {
			return null;
		}

		FileTime fileTime = attributes.creationTime();
		String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));

		builder.append(" " + formattedDateTime + " " + child.getName());

		return builder.toString();

	}

}
