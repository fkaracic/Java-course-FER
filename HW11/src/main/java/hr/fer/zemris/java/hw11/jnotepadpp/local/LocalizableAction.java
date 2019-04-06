package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.AbstractAction;

/**
 * Represents abstract action that is localized.
 * 
 * @author Filip Karacic
 *
 */
public abstract class LocalizableAction extends AbstractAction {
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * String representing key for retaining text displayed for this action.
	 */
	String key;

	/**
	 * Initializes newly created {@code LocalizableAction} object representing
	 * localized abstract action.
	 * 
	 * @param key
	 *            key for retaining text displayed for this component
	 * @param provider
	 *            localization provider
	 */
	public LocalizableAction(String key, ILocalizationProvider provider) {
		this.key = key;

		String translation = provider.getString(key);

		putValue(NAME, translation);

		provider.addLocalizationListener(() -> {
			String translate = provider.getString(key);

			putValue(NAME, translate);

		});
	}

}
