package hr.fer.zemris.java.hw11.jnotepadpp.local.components;

import javax.swing.JMenu;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Represents {@code JMenu} object that is localized.
 * 
 * @author Filip Karacic
 *
 */
public class LJMenu extends JMenu {
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * String representing key for retaining text displayed for this component.
	 */
	String key;

	/**
	 * Initializes newly created {@code LJMenu} object representing localized menu.
	 * 
	 * @param key key for retaining text displayed for this component
	 * @param provider localization provider
	 */
	public LJMenu(String key, ILocalizationProvider provider) {
		this.key = key;

		String translated = provider.getString(key);

		setText(translated);

		provider.addLocalizationListener(() -> {
			String translate = provider.getString(key);

			setText(translate);
		});

	}

}
