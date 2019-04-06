package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

/**
 * Represents model of zero, one or more documents. This model is also iterable.
 * 
 * @author Filip Karacic
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
	/**
	 * Returns newly created document.
	 * 
	 * @return newly created document
	 */
	SingleDocumentModel createNewDocument();

	/**
	 * Returns current selected document.
	 * 
	 * @return current selected document
	 */
	SingleDocumentModel getCurrentDocument();

	/**
	 * Returns loaded document. If already is opened does not open the another one.
	 * 
	 * @param path
	 *            path of the document to be loaded
	 * 
	 * @return loaded document
	 */
	SingleDocumentModel loadDocument(Path path);

	/**
	 * Saves current document to the given path. If the given path is
	 * <code>null</code> the file is saved within it's path.
	 * 
	 * @param model document to be saved
	 * @param newPath path for document to be saved at
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);

	/**Closes the given document.
	 * 
	 * @param model document to be closed
	 */
	void closeDocument(SingleDocumentModel model);

	/**
	 * Adds listener for this multiple document model.
	 * 
	 * @param l listener to be added
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Removes listener for this multiple document model.
	 * 
	 * @param l listener to be removed
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Returns number of document in this model.
	 * 
	 * @return number of document in this model
	 */
	int getNumberOfDocuments();

	/**
	 * Returns document at the given index.
	 * 
	 * @param index index of the document
	 * 
	 * @return document at the given index
	 */
	SingleDocumentModel getDocument(int index);
}
