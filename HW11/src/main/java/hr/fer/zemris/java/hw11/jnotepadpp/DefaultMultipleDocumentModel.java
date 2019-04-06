package hr.fer.zemris.java.hw11.jnotepadpp;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import hr.fer.zemris.java.hw11.Icons;

/**
 * Represents implementation of the multiple document model that contains zero,
 * one or more documents.
 * 
 * @author Filip Karacic
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * List of single documents at this model.
	 */
	private List<SingleDocumentModel> singleDocumentModels;
	/**
	 * Current opened document.
	 */
	private SingleDocumentModel current;
	/**
	 * Listeners for this multiple document model.
	 */
	private List<MultipleDocumentListener> listeners;

	/**
	 * Listener for single document model.
	 */
	private SingleDocumentListener listener;

	/**
	 * Initializes newly created multiple document model.
	 */
	public DefaultMultipleDocumentModel() {
		setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		listeners = new ArrayList<>();
		singleDocumentModels = new ArrayList<>();

		createListener();
		addListener();

	}

	/**
	 * Adds change listener for this model.
	 */
	private void addListener() {
		addChangeListener(event -> {
			if (singleDocumentModels.size() != 0) {
				notifyChange(listener -> listener.currentDocumentChanged(current,
						singleDocumentModels.get(getSelectedIndex())));
				current = singleDocumentModels.get(getSelectedIndex());
			}
		});
	}

	/**
	 * Creates listener for the single document.
	 */
	private void createListener() {
		listener = new SingleDocumentListener() {

			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				if (model.isModified()) {
					setIconAt(getSelectedIndex(), Icons.redDisk);
				} else {
					setIconAt(getSelectedIndex(), Icons.greenDisk);
				}
			}

			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				setTitleAt(getSelectedIndex(), model.getFilePath().getFileName().toString());
				setToolTipTextAt(getSelectedIndex(), model.getFilePath().toString());
			}

		};

	}

	/**
	 * Represents iterator of the single documents from this model.
	 *
	 */
	private class ModelIterator implements Iterator<SingleDocumentModel> {
		/**
		 * Index of the current document.
		 */
		private int index = 0;
		/**
		 * Index of the last document or -1 if there is no one.
		 */
		private int last = -1;
		/**
		 * Current document.
		 */
		private SingleDocumentModel current;

		@Override
		public boolean hasNext() {
			return index < singleDocumentModels.size();
		}

		@Override
		public SingleDocumentModel next() {
			if (!hasNext())
				throw new NoSuchElementException("No more documents.");

			current = singleDocumentModels.get(index++);
			last++;

			setSelectedIndex(singleDocumentModels.indexOf(current));

			return current;
		}

		@Override
		public void remove() {
			if (current == null)
				throw new IllegalStateException("No document to remove");

			singleDocumentModels.remove(current);
			current = null;
			index = last;
			last--;

		}

	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return new ModelIterator();
	}

	@Override
	public SingleDocumentModel createNewDocument() {

		SingleDocumentModel model = new DefaultSingleDocumentModel(null, "");
		singleDocumentModels.add(model);

		

		JScrollPane pane = new JScrollPane(model.getTextComponent());
		addTab("new Document", Icons.greenDisk, pane);

		setSelectedComponent(pane);
		setToolTipTextAt(getSelectedIndex(), "new Document");
		
		
		current = model;
		current.addSingleDocumentListener(listener);
		notifyChange(listener -> listener.documentAdded(model));
		return model;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return current;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {

		SingleDocumentModel existing = existingModel(path);

		if (existing != null) {
			notifyChange(listener -> listener.currentDocumentChanged(current, existing));
			current = existing;
			setSelectedIndex(singleDocumentModels.indexOf(current));
			return current;
		}

		try {
			byte[] okteti = Files.readAllBytes(path);
			String tekst = new String(okteti, StandardCharsets.UTF_8);

			SingleDocumentModel model = new DefaultSingleDocumentModel(path, tekst);
			notifyChange(listener -> listener.documentAdded(model));

			JScrollPane pane = new JScrollPane(model.getTextComponent());

			addTab(model.getFilePath().getFileName().toString(), Icons.greenDisk, pane);

			singleDocumentModels.add(model);
			current = model;
			current.addSingleDocumentListener(listener);

			setSelectedComponent(pane);
			setToolTipTextAt(getSelectedIndex(), model.getFilePath().toString());

			return model;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		
		if(!model.isModified()) {
			return;
		}

		if (!writeTo(model, newPath))
			throw new IllegalStateException("Error writing file.");

		if (newPath != null) {
			model.setFilePath(newPath);
		}

		current.setModified(false);

	}

	/**
	 * Writes given document to the given path.
	 * 
	 * @param model
	 *            document to be written
	 * @param newPath
	 *            path on which to write
	 * 
	 * @return <code>true</code> if document is successfully written
	 */
	private boolean writeTo(SingleDocumentModel model, Path newPath) {
		Path savePath = newPath == null ? model.getFilePath() : newPath;

		byte[] podatci = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);

		try {
			Files.write(savePath, podatci);
		} catch (IOException e) {
			return false;
		}

		return true;
	}

	/**
	 * Returns document within the given path if it is already present in this model.
	 * 
	 * @param path path to be searched
	 * 
	 * @return document within the given path if it is already present in this model
	 */
	private SingleDocumentModel existingModel(Path path) {
		for (SingleDocumentModel model : singleDocumentModels) {
			try {
				if (model.getFilePath() != null)
					if (Files.isSameFile(model.getFilePath(), path))
						return model;
			} catch (IOException e) {
				throw new IllegalArgumentException("Error comparing files.");
			}
		}

		return null;
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		if (getTabCount() == 0) {
			return;
		}

		singleDocumentModels.remove(model);

		if (singleDocumentModels.size() == 0) {
			current = null;
		}

		notifyChange(listener -> listener.documentRemoved(model));
		model.removeSingleDocumentListener(listener);
		removeTabAt(getSelectedIndex());
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(Objects.requireNonNull(l));
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(Objects.requireNonNull(l));
	}

	@Override
	public int getNumberOfDocuments() {
		return singleDocumentModels.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		if (index < 0 || index >= singleDocumentModels.size())
			throw new IllegalArgumentException("There is no document at the given index");

		return singleDocumentModels.get(index);
	}

	/**
	 * Notifies all listeners that change has occurred.
	 * 
	 * @param consumer action performed
	 */
	private void notifyChange(Consumer<MultipleDocumentListener> consumer) {
		for (MultipleDocumentListener listener : listeners) {
			consumer.accept(listener);
		}
	}
}
