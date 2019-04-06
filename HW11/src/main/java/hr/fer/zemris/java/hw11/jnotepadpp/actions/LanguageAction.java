package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * Represents language action. Action provides display in the selected language.
 * 
 * @author Filip Karacic
 *
 */
public class LanguageAction extends LocalizableAction {
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Language tag.
	 */
	String language;

	/**
	 * Initializes newly created language action.
	 * 
	 * @param language language tag
	 * @param key key within translation for this action is mapped
	 * @param provider localization provider
	 */
	public LanguageAction(String language, String key, ILocalizationProvider provider) {
		super(key, provider);

		this.language = language;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		LocalizationProvider.getInstance().setLanguage(language);
	}

}
