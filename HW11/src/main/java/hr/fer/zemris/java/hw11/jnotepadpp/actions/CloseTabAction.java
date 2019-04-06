package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.util.Objects;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Represents close tab action. If the document in the tab has been modified
 * user is asked to save the document before closing. Keyboard shortcut for the
 * given action is ctrl+W.
 * 
 * @author Filip Karacic
 *
 */
public class CloseTabAction extends LocalizableAction {

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
	 * Initializes newly created close tab action.
	 * 
	 * @param model
	 *            multiple document model
	 * @param frame
	 *            frame of the window
	 * @param provider
	 *            localization provider
	 */
	public CloseTabAction(MultipleDocumentModel model, JNotepadPP frame, ILocalizationProvider provider) {
		super("close_tab", provider);

		this.model = Objects.requireNonNull(model);
		this.frame = Objects.requireNonNull(frame);
		this.provider = provider;

		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(provider.getString("close_tab_mnemonic")).getKeyCode());

		putValue(Action.SHORT_DESCRIPTION, provider.getString("close_tab_descr"));

		provider.addLocalizationListener(() -> {
			putValue(Action.SHORT_DESCRIPTION, provider.getString("close_tab_descr"));
			putValue(Action.MNEMONIC_KEY,
					KeyStroke.getKeyStroke(provider.getString("close_tab_mnemonic")).getKeyCode());

		});

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (model.getCurrentDocument().isModified()) {

			String name = model.getCurrentDocument().getFilePath() == null ? "new Document"
					: model.getCurrentDocument().getFilePath().toAbsolutePath().toString();
			int result = JOptionPane.showConfirmDialog(frame, provider.getString("save_file") + ": " + name + " ?",
					provider.getString("save"), JOptionPane.YES_NO_CANCEL_OPTION);

			if (result == JOptionPane.CANCEL_OPTION)
				return;

			if (result == JOptionPane.YES_OPTION) {
				frame.saveDocumentAction.actionPerformed(null);
				
				if(model.getCurrentDocument().isModified()) {
					return;
				}
			}
		}

		model.closeDocument(model.getCurrentDocument());

		if (model.getCurrentDocument() == null) {
			setEnabled(false);
			frame.saveAsDocumentAction.setEnabled(false);
			frame.saveDocumentAction.setEnabled(false);
			frame.statisticalInfoAction.setEnabled(false);
			frame.pasteAction.setEnabled(false);
		}
	}
}
