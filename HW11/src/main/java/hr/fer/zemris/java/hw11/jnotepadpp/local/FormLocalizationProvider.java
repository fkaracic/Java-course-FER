package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * Represents localization provider that registers itself as the given window
 * listener. When window is closed it de-registers itself.
 * 
 * @author Filip Karacic
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	/**
	 * Constructor for this localization provider.
	 * 
	 * @param provider
	 *            localization provider
	 * @param frame
	 *            frame of the window
	 */
	public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
		super(provider);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				connect();
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				disconnect();
			}
		});

	}
}
