package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.Objects;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.Clock;
import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.SingleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Represents action that closes the application. Before closing if any document
 * is modified user is asked to save the work. Keyboard shortcut for the given
 * action is alt+F5.
 * 
 * @author Filip Karacic
 *
 */
public class ExitAction extends LocalizableAction {

	/**
	 * Date and time displayer.
	 */
	private Clock clock;

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
	 * Initializes newly created exit action.
	 * 
	 * @param model
	 *            multiple document model
	 * @param frame
	 *            frame of the window
	 * @param clock
	 *            date and time displayer
	 * @param provider
	 *            localization provider
	 */
	public ExitAction(MultipleDocumentModel model, JNotepadPP frame, Clock clock, ILocalizationProvider provider) {
		super("exit", provider);

		this.model = Objects.requireNonNull(model);
		this.frame = Objects.requireNonNull(frame);
		this.clock = clock;
		this.provider = provider;

		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt F5"));
		putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(provider.getString("exit_mnemonic")).getKeyCode());

		putValue(Action.SHORT_DESCRIPTION, provider.getString("exit_descr"));

		provider.addLocalizationListener(() -> {
			putValue(Action.SHORT_DESCRIPTION, provider.getString("exit_descr"));
			putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(provider.getString("exit_mnemonic")).getKeyCode());
		});
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (model.getNumberOfDocuments() != 0) {
			Iterator<SingleDocumentModel> it = model.iterator();

			while (it.hasNext()) {
				SingleDocumentModel model2 = it.next();

				if (model2.isModified()) {
					String name = model2.getFilePath() == null ? "new Document"
							: model2.getFilePath().toAbsolutePath().toString();

					int result = JOptionPane.showConfirmDialog(frame,
							provider.getString("save_file") + ": " + name + " ?", provider.getString("save"),
							JOptionPane.YES_NO_CANCEL_OPTION);

					if (result == JOptionPane.CANCEL_OPTION)
						return;

					if (result == JOptionPane.YES_OPTION) {
						frame.saveDocumentAction.actionPerformed(null);
						
						if(model.getCurrentDocument().isModified()) {
							return;
						}
					}
				}

				model.closeDocument(model2);
				it.remove();
			}

		}

		clock.setStopRequested(true);
		frame.dispose();
	}

}
