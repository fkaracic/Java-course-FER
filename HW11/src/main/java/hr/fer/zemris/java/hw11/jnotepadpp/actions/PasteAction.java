package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Represents paste action. Text is pasted from the current caret position onwards. Keyboard shortcut for the given action is
 * ctrl+V.
 * 
 * @author Filip Karacic
 *
 */
public class PasteAction extends LocalizableAction {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Multiple document model.
	 */
	private MultipleDocumentModel model;
	
	/**
	 * Initializes newly created paste action.
	 * 
	 * @param model multiple document model
	 * @param frame frame of the window
	 * @param provider localization provider
	 */
	public PasteAction(MultipleDocumentModel model, JNotepadPP frame, ILocalizationProvider provider) {
		super("paste", provider);

		this.model = model;

		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(provider.getString("paste_mnemonic")).getKeyCode());

		putValue(Action.SHORT_DESCRIPTION, provider.getString("paste_descr"));

		provider.addLocalizationListener(() -> {
			putValue(Action.SHORT_DESCRIPTION, provider.getString("paste_descr"));
			putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(provider.getString("paste_mnemonic")).getKeyCode());
		});
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (model.getCurrentDocument() != null) {
			model.getCurrentDocument().getTextComponent().paste();
		}

	}

}
