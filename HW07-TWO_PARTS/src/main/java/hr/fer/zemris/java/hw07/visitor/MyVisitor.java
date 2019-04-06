package hr.fer.zemris.java.hw07.visitor;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;

import hr.fer.zemris.java.hw07.shell.Environment;

/**
 * Visitor of the files printing tree structure of the given directory.
 * 
 * @author Filip Karacic
 *
 */
public class MyVisitor implements FileVisitor<Path> {
	/**
	 * Environment of the shell.
	 */
	private Environment environment;

	/**
	 * Current level of the directory.
	 */
	private int level = 0;

	/**
	 * Initializes newly created visitor.
	 * 
	 * @param environment
	 *            environment of the shell
	 * @throws NullPointerException if the {@code environment} is <code>null</code>
	 */
	public MyVisitor(Environment environment) {
		this.environment = Objects.requireNonNull(environment);
	}

	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		level--;
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

		String print = level == 0 ? dir.getFileName().toString()
				: String.format("%" + level * 2 + "s%s", "", dir.getFileName());

		environment.writeln(print);

		level++;

		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		String print = level == 0 ? file.getFileName().toString()
				: String.format("%" + level * 2 + "s%s", "", file.getFileName());

		environment.writeln(print);

		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		throw new IOException(exc);
	}

}
