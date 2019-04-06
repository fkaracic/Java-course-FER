package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Represents open document action. Opens selected document if it hasn't already
 * been opened. Keyboard shortcut for the given action is
 * ctrl+O.
 * 
 * @author Filip Karacic
 *
 */
public class OpenDocumentAction extends LocalizableAction {

	/**
	 * Frame of the window.
	 */
	private JNotepadPP frame;

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Multiple document model.
	 */
	private MultipleDocumentModel model;

	/**
	 * Localization provider.
	 */
	private ILocalizationProvider provider;

	/**
	 * Initializes newly created open document action.
	 * 
	 * @param model
	 *            multiple document model
	 * @param frame
	 *            frame of the window
	 * @param provider
	 *            localization provider
	 */
	public OpenDocumentAction(MultipleDocumentModel model, JNotepadPP frame, ILocalizationProvider provider) {
		super("open", provider);

		this.model = Objects.requireNonNull(model);
		this.frame = Objects.requireNonNull(frame);
		this.provider = provider;

		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(provider.getString("open_mnemonic")).getKeyCode());

		putValue(Action.SHORT_DESCRIPTION, provider.getString("open_doc_descr"));

		provider.addLocalizationListener(() -> {
			putValue(Action.SHORT_DESCRIPTION, provider.getString("open_doc_descr"));
			putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(provider.getString("open_mnemonic")).getKeyCode());
		});
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle(provider.getString("open_file"));

		if (fc.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) {
			return;
		}

		java.io.File fileName = fc.getSelectedFile();
		Path filePath = fileName.toPath();

		if (!Files.isReadable(filePath)) {
			JOptionPane.showMessageDialog(frame,
					provider.getString("file") + " " + fileName.getAbsolutePath() + " "
							+ provider.getString("not_exists") + "!",
					provider.getString("error"), JOptionPane.ERROR_MESSAGE);
			return;
		}

		model.loadDocument(filePath);

		frame.closeTabAction.setEnabled(true);
		frame.saveAsDocumentAction.setEnabled(true);
		frame.saveDocumentAction.setEnabled(true);
		frame.statisticalInfoAction.setEnabled(true);
		frame.pasteAction.setEnabled(true);
	}

}
