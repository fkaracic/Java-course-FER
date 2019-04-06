package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Represents the implementation of the single document model that has its text
 * component, path, modification indicator and listeners for changes of this
 * model.
 * 
 * @author Filip Karacic
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {
	/**
	 * Text component of this model.
	 */
	private JTextArea editor;
	/**
	 * Path of this document.
	 */
	private Path filePath;
	/**
	 * Listeners for this model.
	 */
	private List<SingleDocumentListener> listeners;
	/**
	 * Modification indicator.
	 */
	private boolean modified;

	/**
	 * Initializes newly created {@code DefaultSingleDocumentModel} object representing single document.
	 * 
	 * @param filePath path of the file
	 * @param content content of the file
	 */
	public DefaultSingleDocumentModel(Path filePath, String content) {

		this.filePath = filePath;

		editor = new JTextArea(content);
		listeners = new ArrayList<>();

		addDocumentListener();
	}

	/**
	 * Adds document listener for this model.
	 */
	private void addDocumentListener() {
		editor.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				setModified(true);

			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				setModified(true);

			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				setModified(true);

			}
		});

	}

	@Override
	public JTextArea getTextComponent() {
		return editor;
	}

	@Override
	public Path getFilePath() {
		return filePath;
	}

	@Override
	public void setFilePath(Path path) {
		filePath = Objects.requireNonNull(path);

		notifyChange(listener -> listener.documentFilePathUpdated(this));
	}
	
	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;

		notifyChange(listener -> listener.documentModifyStatusUpdated(this));
	}

	/**
	 * Notifies all listeners that change has happened.
	 * 
	 * @param consumer operation performed
	 */
	private void notifyChange(Consumer<SingleDocumentListener> consumer) {
		for (SingleDocumentListener listener : listeners) {
			consumer.accept(listener);
		}
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(Objects.requireNonNull(l));
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(Objects.requireNonNull(l));
	}

}
