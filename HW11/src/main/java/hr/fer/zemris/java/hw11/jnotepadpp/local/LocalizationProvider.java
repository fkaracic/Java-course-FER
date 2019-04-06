package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Represents localization provider. It is not possible to create instances of
 * this class, only one static instance is created and can be obtained by the
 * method getInstance.
 * 
 * @author Filip Karacic
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {
	/**
	 * Current language tag.
	 */
	private String language;
	/**
	 * Object containing locale-specific.
	 */
	private ResourceBundle bundle;
	/**
	 * Instance of this class.
	 */
	private static LocalizationProvider provider;

	/**
	 * Initializes newly created object.
	 */
	private LocalizationProvider() {
		language = "en";
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.translations.prijevodi", locale);
	}

	/**
	 * Returns instance of this class. Only once created.
	 * 
	 * @return instance of this class
	 */
	public static LocalizationProvider getInstance() {
		if(provider == null) {
			 provider = new LocalizationProvider();
		}
		
		return provider;
	}

	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

	/**
	 * Sets language to the given language.
	 * 
	 * @param language language to be set
	 */
	public void setLanguage(String language) {
		this.language = language;
		
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.translations.prijevodi", locale);

		fire();
	}

}
