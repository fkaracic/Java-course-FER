package hr.fer.zemris.java.hw07.shell;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Paths;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw07.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.CdShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.CptreeShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.DropdShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.ListdShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.MassrenameShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.PopdShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.PushdShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.PwdShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.RmtreeShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.TreeShellCommand;

/**
 * Shell for work with files. User can enter keyword help to see all available
 * commands and also can enter help commandName to gain informations about the
 * specified command. Shell allows user copying, listing, moving and many more
 * work with the files and directories.
 * <p>
 * User can enter inputs through more lines but at the end of each line symbol
 * for multilines must be provided. Any path given in commands is resolved with
 * the current directory.
 * 
 * @author Filip Karacic
 *
 */
public class MyShell {

	/**
	 * Method called when program starts.
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to MyShell v 1.0");

		SortedMap<String, ShellCommand> commands = new TreeMap<>();

		addCommands(commands);

		try (InputStreamReader in = new InputStreamReader(System.in);
				OutputStreamWriter out = new OutputStreamWriter(System.out)) {

			myShell(commands, in, out);

		} catch (IOException e) {
			System.out.println("Error opening streams: " + e.getMessage());
		}
	}

	/**
	 * Represents shell that executes commands user enters.
	 * 
	 * @param commands
	 *            map containing all available commands
	 * @param in
	 *            input stream
	 * @param out
	 *            output stream
	 */
	private static void myShell(SortedMap<String, ShellCommand> commands, InputStreamReader in,
			OutputStreamWriter out) {
		Environment environment = new EnvironmentImpl(commands, Paths.get("."), in, out);
		ShellStatus state = ShellStatus.CONTINUE;

		while (state == ShellStatus.CONTINUE) {
			environment.write(environment.getPromptSymbol().toString() + " ");

			String line = environment.readLine();

			if (line == null) {
				environment.writeln("\nEOF is read. Terminating program.");
				break;
			}

			line = line.trim();

			if (line.endsWith(environment.getMorelinesSymbol().toString())) {
				line = moreLines(line, environment);
			}

			String command = extractCommand(line);
			ShellCommand shellCommand;

			if ((shellCommand = environment.commands().get(command)) == null) {
				environment.writeln("Invalid command.");
				continue;
			}

			state = shellCommand.executeCommand(environment, line);
		}

	}

	/**
	 * @param line
	 *            line to extract command from
	 * @return {@code String} containing extracted command name
	 */
	private static String extractCommand(String line) {
		int index = line.indexOf(" ");

		if (index == -1)
			return line.toLowerCase();

		return line.toLowerCase().substring(0, index);
	}

	/**
	 * Returns {@code String} containing content user entered through two or more
	 * lines.
	 * 
	 * @param line
	 *            line containing morelines symbol
	 * @param environment
	 *            environment of the shell
	 * 
	 * @return {@code String} containing content user entered through two or more
	 *         lines
	 */
	private static String moreLines(String line, Environment environment) {
		StringBuilder lines = new StringBuilder();
		String read = line;

		do {
			lines.append(read.substring(0, read.length() - 1));

			environment.write(environment.getMultilineSymbol() + " ");
			read = environment.readLine();
		} while (read.endsWith(environment.getMorelinesSymbol().toString()));

		lines.append(read);

		return lines.toString();
	}

	/**
	 * Adds all available commands to the given map.
	 * 
	 * @param commands
	 *            map in which commands will be added
	 */
	private static void addCommands(SortedMap<String, ShellCommand> commands) {
		commands.put("exit", new ExitShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("help", new HelpShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("cat", new CatShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("symbol", new SymbolShellCommand());
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("pwd", new PwdShellCommand());
		commands.put("cd", new CdShellCommand());
		commands.put("pushd", new PushdShellCommand());
		commands.put("popd", new PopdShellCommand());
		commands.put("listd", new ListdShellCommand());
		commands.put("dropd", new DropdShellCommand());
		commands.put("rmtree", new RmtreeShellCommand());
		commands.put("cptree", new CptreeShellCommand());
		commands.put("massrename", new MassrenameShellCommand());
	}
}
