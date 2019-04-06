package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Represents provider of the localization.
 * 
 * @author Filip Karacic
 *
 */
public interface ILocalizationProvider {

	/**
	 * Adds listener for this localization provider.
	 * 
	 * @param l localization listener
	 */
	void addLocalizationListener(ILocalizationListener l);

	/**
	 * Removes listener for this localization provider.
	 * 
	 * @param l localization listener
	 */
	void removeLocalizationListener(ILocalizationListener l);

	/**
	 * Provides translation within the given key for current localization.
	 * 
	 * @param key key within translation is mapped
	 * 
	 * @return {@code String} representing translation within the given key for current localizations
	 */
	String getString(String key);
}
