package hr.fer.zemris.java.hw11.jnotepadpp;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Represents implementation of the listener for multiple document model.
 * 
 * @author Filip Karacic
 *
 */
public class MultipleDocumentListenerImpl implements MultipleDocumentListener {

	/**
	 * Frame of the model.
	 */
	private JNotepadPP frame;
	/**
	 * Model of multiple documents.
	 */
	private MultipleDocumentModel model;
	/**
	 * Labels displaying at the statusbar.
	 */
	private JLabel[] labels;
	/**
	 * Localization provider.
	 */
	private ILocalizationProvider provider;

	/**
	 * Constructor for this listener implementation.
	 * 
	 * @param frame
	 *            frame of the document model
	 * @param model
	 *            multiple document model
	 * @param labels
	 *            labels of the statusbar
	 * @param provider
	 *            localization provider
	 */
	public MultipleDocumentListenerImpl(JNotepadPP frame, MultipleDocumentModel model, JLabel[] labels,
			ILocalizationProvider provider) {
		this.frame = frame;
		this.model = model;
		this.labels = labels;
		this.provider = provider;
	}

	@Override
	public void documentRemoved(SingleDocumentModel model) {
		currentDocumentChanged(model, this.model.getCurrentDocument());
	}

	@Override
	public void documentAdded(SingleDocumentModel model) {
		displayTitle(model);
		displayStatus(model);
	}

	@Override
	public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
		displayTitle(currentModel);
		displayStatus(currentModel);

		if (currentModel != null) {
			JTextArea editor = currentModel.getTextComponent();

			editor.addCaretListener(l -> {
				int len = editor.getCaret().getDot() - editor.getCaret().getMark();

				frame.cutAction.setEnabled(len != 0);
				frame.copyAction.setEnabled(len != 0);
				frame.uniqueAction.setEnabled(len != 0);
				frame.toLowerAction.setEnabled(len != 0);
				frame.toUpperAction.setEnabled(len != 0);
				frame.invertAction.setEnabled(len != 0);
				frame.ascendingAction.setEnabled(len != 0);
				frame.descendingAction.setEnabled(len != 0);
			});
		}
	}

	/**
	 * Displays status of the document.
	 * 
	 * @param model
	 *            current document
	 */
	private void displayStatus(SingleDocumentModel model) {

		if (model == null) {
			setLabels();
			return;
		}

		JTextArea editor = model.getTextComponent();
		changeLabels(editor);

		editor.addCaretListener(l -> {
			changeLabels(editor);

		});
	}

	/**
	 * Refreshes labels of statusbar.
	 * 
	 * @param editor
	 *            text area.
	 */
	private void changeLabels(JTextArea editor) {
		int length = editor.getText().length();
		labels[0].setText(provider.getString("length") + ":" + length);

		try {
			int offset = editor.getCaretPosition();
			int line = editor.getLineOfOffset(offset);

			int off = editor.getLineStartOffset(line);
			int col = offset - off + 1;

			labels[1].setText(provider.getString("ln") + ":" + (line + 1));
			labels[2].setText(provider.getString("col") + ":" + col);

			int sel = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());

			labels[3].setText(provider.getString("sel") + ":" + sel);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Sets status of the labels to the '-'.
	 */
	private void setLabels() {
		labels[0].setText(provider.getString("length") + ":-");
		labels[1].setText(provider.getString("ln") + ":-");
		labels[2].setText(provider.getString("col") + ":-");
		labels[3].setText(provider.getString("sel") + ":-");
	}

	/**
	 * Displays title of the model's frame
	 * 
	 * @param model
	 *            current document
	 */
	private void displayTitle(SingleDocumentModel model) {
		if (model == null) {
			frame.setTitle("JNotepad++");
			return;
		}

		if (model != null && model.getFilePath() != null) {
			frame.setTitle(model.getFilePath().toAbsolutePath() + " - JNotepad++");

		} else {
			frame.setTitle("new Document" + " - JNotepad++");
		}
	}

}
