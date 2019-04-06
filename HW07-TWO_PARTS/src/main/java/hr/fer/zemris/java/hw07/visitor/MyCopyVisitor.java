package hr.fer.zemris.java.hw07.visitor;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;

/**
 * Visitor of the files that copies all of the files from the current directory
 * to the destination directory.
 * 
 * @author Filip Karacic
 *
 */
public class MyCopyVisitor implements FileVisitor<Path> {
	/**
	 * Path of the source directory.
	 */
	private Path source;
	/**
	 * Path of the destination directory.
	 */
	private Path destination;;

	/**
	 * Initializes newly created visitor.
	 * 
	 * @param source path of the source directory
	 * @param destination path of the destination directory
	 */
	public MyCopyVisitor(Path source, Path destination) {
		this.source = Objects.requireNonNull(source);
		this.destination = Objects.requireNonNull(destination);
	}

	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attr) throws IOException {
		Path dest = destination.resolve(source.relativize(dir));

		Files.createDirectory(dest);

		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attr) throws IOException {
		Path dest = destination.resolve(source.relativize(file));

		Files.copy(file, dest);

		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		throw new IOException(exc);
	}

}
