package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.util.Objects;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Represents create new document action. Creates new blank document. Keyboard
 * shortcut for the given action is ctrl+N.
 * 
 * @author Filip Karacic
 *
 */
public class CreateNewDocumentAction extends LocalizableAction {

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
	 * Initializes newly created create new document action.
	 * 
	 * @param model
	 *            multiple document model
	 * @param frame
	 *            frame of the window
	 * @param provider
	 *            localization provider
	 */
	public CreateNewDocumentAction(MultipleDocumentModel model, JNotepadPP frame, ILocalizationProvider provider) {
		super("new", provider);

		this.model = Objects.requireNonNull(model);
		this.frame = frame;

		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(provider.getString("new_mnemonic")).getKeyCode());

		putValue(Action.SHORT_DESCRIPTION, provider.getString("new_descr"));

		provider.addLocalizationListener(() -> {
			putValue(Action.SHORT_DESCRIPTION, provider.getString("new_descr"));
			putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(provider.getString("new_mnemonic")).getKeyCode());
		});
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		model.createNewDocument();

		frame.closeTabAction.setEnabled(true);
		frame.saveAsDocumentAction.setEnabled(true);
		frame.saveDocumentAction.setEnabled(true);
		frame.statisticalInfoAction.setEnabled(true);
		frame.pasteAction.setEnabled(true);
	}

}
