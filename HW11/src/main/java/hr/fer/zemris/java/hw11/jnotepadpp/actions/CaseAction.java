package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.util.function.Function;

import javax.swing.Action;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Represents case modification function. To lower case, to upper case or
 * inversion of case. Keyboard shortcut for to lower action is ctrl+L, for to
 * upper is ctrl+U and for inversion ctrl+I.
 * 
 * @author Filip Karacic
 *
 */
public class CaseAction extends LocalizableAction {

	/**
	 * Case modification function.
	 */
	private Function<String, String> function;

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Multiple document model.
	 */
	private MultipleDocumentModel model;

	/**
	 * Initializes newly created case modification action.
	 * 
	 * @param key
	 *            key within name translation is mapped
	 * @param desc_key
	 *            key within short description translation is mapped
	 * @param key_stroke
	 *            key within stroke translation is mapped
	 * @param mnemonic_key
	 *            key within mnemonic translation is mapped
	 * @param function
	 *            case modification function
	 * @param model
	 *            multiple document model
	 * @param frame
	 *            frame of the window
	 * @param provider
	 *            localization provider
	 */
	public CaseAction(String key, String desc_key, String key_stroke, String mnemonic_key,
			Function<String, String> function, MultipleDocumentModel model, JNotepadPP frame,
			ILocalizationProvider provider) {

		super(key, provider);

		this.function = function;
		this.model = model;

		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(key_stroke));
		putValue(Action.SHORT_DESCRIPTION, provider.getString(desc_key));
		putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(provider.getString(mnemonic_key)).getKeyCode());

		provider.addLocalizationListener(() -> {
			putValue(Action.SHORT_DESCRIPTION, provider.getString(desc_key));
			putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(provider.getString(mnemonic_key)).getKeyCode());
		});

		setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JTextArea editor = model.getCurrentDocument().getTextComponent();

		int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
		int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());

		String selected = editor.getSelectedText();

		try {
			editor.getDocument().remove(offset, len);
			editor.getDocument().insertString(offset, function.apply(selected), null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

}
