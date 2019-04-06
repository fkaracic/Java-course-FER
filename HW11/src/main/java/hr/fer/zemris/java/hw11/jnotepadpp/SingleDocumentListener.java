package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Represents listener for the single document. Listens for changes of the
 * document.
 * 
 * @author Filip Karacic
 *
 */
public interface SingleDocumentListener {
	/**
	 * Performed when document's text is modified.
	 * 
	 * @param model
	 *            document modified
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);

	/**
	 * Performed when document's path is modified.
	 * 
	 * @param model
	 *            document modified
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
}
