package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * Represents model of a single document.
 * 
 * @author Filip Karacic
 *
 */
public interface SingleDocumentModel {
	/**
	 * Returns text component of this document.
	 * 
	 * @return text component of this document
	 */
	JTextArea getTextComponent();

	/**
	 * Returns file path of this document.
	 * 
	 * @return file path of this document
	 */
	Path getFilePath();

	/**
	 * Sets path of this document to the given path.
	 * 
	 * @param path path to be set
	 */
	void setFilePath(Path path);

	/**
	 * Returns <code>true</code> if this document is modified.
	 * 
	 * @return <code>true</code> if this document is modified
	 */
	boolean isModified();

	/**
	 * Sets modification of this document to the given value.
	 * 
	 * @param modified indicator of modification
	 */
	void setModified(boolean modified);

	/**
	 * Adds listener to this single document model.
	 * 
	 * @param l listener to be added
	 */
	void addSingleDocumentListener(SingleDocumentListener l);

	/**
	 * Removes listener from this single document model.
	 * 
	 * @param l listener to be removed
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}
