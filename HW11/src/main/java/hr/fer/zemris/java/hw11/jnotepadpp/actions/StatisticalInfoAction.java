package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Represents action that displays statistical info: current characters,
 * non-blank characters and number of lines. Keyboard shortcut for the given action is
 * alt+F6.
 * 
 * @author Filip Karacic
 *
 */
public class StatisticalInfoAction extends LocalizableAction {

	/**
	 * Frame of the window.
	 */
	private JFrame frame;

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
	 * Initializes newly created statistical info action.
	 * 
	 * @param model
	 *            multiple document model
	 * @param frame
	 *            frame of the window
	 * @param provider
	 *            localization provider
	 */
	public StatisticalInfoAction(MultipleDocumentModel model, JFrame frame, ILocalizationProvider provider) {
		super("statistics", provider);
		this.model = model;
		this.frame = frame;
		this.provider = provider;

		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt F6"));
		putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(provider.getString("statistics_mnemonic")).getKeyCode());

		putValue(Action.SHORT_DESCRIPTION, provider.getString("stat_info_descr"));

		provider.addLocalizationListener(() -> {
			putValue(Action.SHORT_DESCRIPTION, provider.getString("stat_info_descr"));
			putValue(Action.MNEMONIC_KEY,
					KeyStroke.getKeyStroke(provider.getString("statistics_mnemonic")).getKeyCode());
		});

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		JTextArea editor = model.getCurrentDocument().getTextComponent();
		String text = editor.getText();

		int charactersNumber = text.length();
		int nonBlankNumber = countNonBlank(text);
		int lineNumber = editor.getLineCount();

		String infoOutput = provider.getString("document_contains") + ": " + charactersNumber + " "
				+ provider.getString("characters") + ", " + nonBlankNumber + " " + provider.getString("non_blank") + " "
				+ lineNumber + " " + provider.getString("lines") + ".";

		JOptionPane.showMessageDialog(frame, infoOutput, provider.getString("statistical_info"),
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Returns number of non-blank characters.
	 * 
	 * @param text
	 *            text to be checked
	 * 
	 * @return number of non-blank characters
	 */
	private int countNonBlank(String text) {
		char[] data = text.toCharArray();
		int count = 0;

		for (int i = 0; i < data.length; i++) {
			switch (data[i]) {
			case ' ':
			case '\n':
			case '\r':
			case '\t':
				break;
			default:
				count++;
			}
		}

		return count;
	}

}
