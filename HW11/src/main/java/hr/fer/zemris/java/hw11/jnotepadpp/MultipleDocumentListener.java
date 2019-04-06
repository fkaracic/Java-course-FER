package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Represents listener for tabbed pane containing zero, one or more documents.
 * 
 * @author Filip Karacic
 *
 */
public interface MultipleDocumentListener {
	/**
	 * Performed when current document changes.
	 * 
	 * @param previousModel previous document
	 * @param currentModel current document
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);

	/**
	 * Performed when document is added.
	 * 
	 * @param model added document
	 */
	void documentAdded(SingleDocumentModel model);

	/**
	 * Performed when document is removed.
	 * 
	 * @param model removed document.
	 */
	void documentRemoved(SingleDocumentModel model);
}
