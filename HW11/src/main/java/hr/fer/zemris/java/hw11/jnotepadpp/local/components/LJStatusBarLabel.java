package hr.fer.zemris.java.hw11.jnotepadpp.local.components;

import javax.swing.JLabel;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Represents {@code JLabel} object that is localized and used for statusbar.
 * 
 * @author Filip Karacic
 *
 */
public class LJStatusBarLabel extends JLabel {
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * String representing key for retaining text displayed for this component.
	 */
	String key;

	/**
	 * Initializes newly created {@code LJStatusBarLabel} object representing
	 * localized menu.
	 * 
	 * @param key
	 *            key for retaining text displayed for this component
	 * @param provider
	 *            localization provider
	 */
	public LJStatusBarLabel(String key, ILocalizationProvider provider) {
		this.key = key;

		String translated = provider.getString(key);

		setText(translated + ":");

		provider.addLocalizationListener(() -> {
			actionPerformed(provider);
		});
	}

	/**
	 * Action performed for the localization listener.
	 * 
	 * @param provider
	 *            localization provider
	 */
	private void actionPerformed(ILocalizationProvider provider) {
		String translate = provider.getString(key);

		String current = getText();

		int index = current.indexOf(":");

		String str = current.substring(index);

		setText(translate + str);
	}

}
