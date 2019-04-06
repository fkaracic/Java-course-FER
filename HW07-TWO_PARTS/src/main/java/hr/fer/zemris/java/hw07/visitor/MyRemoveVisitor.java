package hr.fer.zemris.java.hw07.visitor;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Visitor of the files that removes all of the files from the given directory.
 * 
 * @author Filip Karacic
 *
 */
public class MyRemoveVisitor implements FileVisitor<Path> {

	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		if (!dir.toFile().delete())
			throw new IOException("Unable to delete directory: " + dir.getFileName());

		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attr) throws IOException {
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attr) throws IOException {
		if (!file.toFile().delete())
			throw new IOException("Unable to delete file: " + file.getFileName());

		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		throw new IOException(exc);
	}

}
