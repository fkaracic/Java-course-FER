package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.util.Objects;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Represents save document as action. User can select path within file will be
 * saved. Keyboard shortcut for the given action is
 * ctrl+alt+S.
 * 
 * @author Filip Karacic
 *
 */
public class SaveAsDocumentAction extends LocalizableAction {

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
	 * Initializes newly created save as action.
	 * 
	 * @param model
	 *            multiple document model
	 * @param frame
	 *            frame of the window
	 * @param provider
	 *            localization provider
	 */
	public SaveAsDocumentAction(MultipleDocumentModel model, JFrame frame, ILocalizationProvider provider) {
		super("save_as", provider);

		this.model = Objects.requireNonNull(model);
		this.frame = Objects.requireNonNull(frame);
		this.provider = provider;

		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt S"));
		putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(provider.getString("save_as_mnemonic")).getKeyCode());

		putValue(Action.SHORT_DESCRIPTION, provider.getString("save_as_descr"));

		provider.addLocalizationListener(() -> {
			putValue(Action.SHORT_DESCRIPTION, provider.getString("save_as_descr"));
			putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(provider.getString("save_as_mnemonic")).getKeyCode());
		});
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle(provider.getString("save_as"));

		if (jfc.showSaveDialog(frame) != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(frame, provider.getString("nothing_saved") + "!",
					provider.getString("warning"), JOptionPane.WARNING_MESSAGE);
			return;
		}

		model.saveDocument(model.getCurrentDocument(), jfc.getSelectedFile().toPath());

		JOptionPane.showMessageDialog(frame, provider.getString("file_saved"), provider.getString("information"),
				JOptionPane.INFORMATION_MESSAGE);
	}
}
