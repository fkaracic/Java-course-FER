package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Represents copy selected text action. Keyboard shortcut for the given action
 * is ctrl+C.
 * 
 * @author Filip Karacic
 *
 */
public class CopyAction extends LocalizableAction {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Multiple document model.
	 */
	private MultipleDocumentModel model;

	/**
	 * Initializes newly created copy action.
	 * 
	 * @param model
	 *            multiple document model
	 * @param frame
	 *            frame of the window
	 * @param provider
	 *            localization provider
	 */
	public CopyAction(MultipleDocumentModel model, JNotepadPP frame, ILocalizationProvider provider) {
		super("copy", provider);

		this.model = model;

		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(provider.getString("copy_mnemonic")).getKeyCode());

		putValue(Action.SHORT_DESCRIPTION, provider.getString("copy_descr"));

		provider.addLocalizationListener(() -> {
			putValue(Action.SHORT_DESCRIPTION, provider.getString("copy_descr"));
			putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(provider.getString("copy_mnemonic")).getKeyCode());
		});

		setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		model.getCurrentDocument().getTextComponent().copy();
	}

}
