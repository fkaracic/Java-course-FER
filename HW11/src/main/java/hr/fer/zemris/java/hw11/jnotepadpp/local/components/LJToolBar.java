package hr.fer.zemris.java.hw11.jnotepadpp.local.components;

import javax.swing.JToolBar;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Represents {@code JToolBar} object that is localized.
 * 
 * @author Filip Karacic
 *
 */
public class LJToolBar extends JToolBar {
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * String representing key for retaining text displayed for this component.
	 */
	String key;
	
	/**
	 * Initializes newly created {@code LJToolBar} object representing localized menu.
	 * 
	 * @param key key for retaining text displayed for this component
	 * @param provider localization provider
	 */
	public LJToolBar(String key, ILocalizationProvider provider) {
		this.key = key;
		
		String translated = provider.getString(key);
		
		setName(translated);
		
		provider.addLocalizationListener(()-> {
			String translate = provider.getString(key);
			
			setName(translate);
			updateUI();
		});
		
	}
	
}
