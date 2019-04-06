package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.swing.Action;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Represents action of sort of the selected lines ascending or descending.
 * Lines are sorted within the rules of the current language. Keyboard shortcut for the ascending sort is
 * alt+F2 and for descenindg is alt+F3.
 * 
 * @author Filip Karacic
 *
 */
public class SortAction extends LocalizableAction {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Key within name translation is mapped.
	 */
	private String key;
	
	/**
	 * Multiple document model.
	 */
	private MultipleDocumentModel model;

	/**
	 * Localization provider.
	 */
	private ILocalizationProvider provider;

	/**
	 * Initializes newly created sort action.
	 * 
	 * @param key
	 *            key within name translation is mapped
	 * @param key_descr
	 *            key within short description translation is mapped
	 * @param key_stroke
	 *            key within stroke translation is mapped
	 * @param key_mnemonic
	 *            key within mnemonic translation is mapped
	 * @param model
	 *            multiple document model
	 * @param frame
	 *            frame of the window
	 * @param provider
	 *            localization provider
	 */
	public SortAction(String key, String key_stroke, String key_descr, String key_mnemonic,
			MultipleDocumentModel model, JNotepadPP frame, ILocalizationProvider provider) {

		super(key, provider);
		
		this.key = key;
		this.model = model;
		this.provider = provider;

		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(key_stroke));
		putValue(Action.SHORT_DESCRIPTION, provider.getString(key_descr));
		putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(provider.getString(key_mnemonic)).getKeyCode());

		provider.addLocalizationListener(() -> {
			putValue(Action.SHORT_DESCRIPTION, provider.getString(key_descr));
			putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(provider.getString(key_mnemonic)).getKeyCode());
		});

		setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		JTextArea editor = model.getCurrentDocument().getTextComponent();

		int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
		int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
		String selected = editor.getSelectedText();

		String[] lines = selected.split("\n");
		if (lines.length < 2)
			return;

		List<String> list = new ArrayList<>();

		for (String line : lines) {
			list.add(line);
		}

		Locale locale = Locale.forLanguageTag(provider.getString("language_tag"));
		Collator collator = Collator.getInstance(locale);

		if(key.equals("ascending")) {
			Collections.sort(list, (s1, s2) -> collator.compare(s1, s2));
		}else {
			Collections.sort(list, (s1, s2) -> collator.compare(s2, s1));
		}

		StringBuilder build = new StringBuilder();
		for (String line : list) {
			build.append(line + "\n");
		}

		try {
			editor.getDocument().remove(offset, len);
			editor.getDocument().insertString(offset, build.toString(), null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
}
