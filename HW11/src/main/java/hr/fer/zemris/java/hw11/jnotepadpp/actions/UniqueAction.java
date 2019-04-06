package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.Action;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Represents localizable action of removing unique lines from selected text.
 * Only first occurrence is retained. Keyboard shortcut for the given action is
 * ctrl+U.
 * 
 * @author Filip Karacic
 *
 */
public class UniqueAction extends LocalizableAction {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Multiple document model.
	 */
	private MultipleDocumentModel model;

	/**
	 * Initializes newly created unique action.
	 * 
	 * @param model
	 *            multiple document model
	 * @param frame
	 *            frame of the window
	 * @param provider
	 *            localization provider
	 */
	public UniqueAction(MultipleDocumentModel model, JNotepadPP frame, ILocalizationProvider provider) {
		super("unique", provider);

		this.model = model;

		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
		putValue(Action.SHORT_DESCRIPTION, provider.getString("unique_descr"));
		putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(provider.getString("unique_mnemonic")).getKeyCode());

		provider.addLocalizationListener(() -> {
			putValue(Action.SHORT_DESCRIPTION, provider.getString("unique_descr"));
			putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(provider.getString("unique_mnemonic")).getKeyCode());
		});

		setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		JTextArea editor = model.getCurrentDocument().getTextComponent();
		Set<String> uniques = new LinkedHashSet<>();

		int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
		int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
		String selected = editor.getSelectedText();

		StringBuilder build = new StringBuilder();
		for (String line : selected.split("\n")) {
			if (uniques.add(line)) {
				build.append(line + "\n");
			}
		}

		try {
			editor.getDocument().remove(offset, len);
			editor.getDocument().insertString(offset, build.toString(), null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}

	}

}
