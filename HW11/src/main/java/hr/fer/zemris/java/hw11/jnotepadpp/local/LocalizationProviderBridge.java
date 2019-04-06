package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Represents localization provider bridge. It is a decorator for some other
 * localization provider.
 * 
 * @author Filip Karacic
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {
	/**
	 * <code>true</code> if provider is connected.
	 */
	private boolean connected;
	/**
	 * Localization provider.
	 */
	private ILocalizationProvider provider;
	/**
	 * Localization listener.
	 */
	private ILocalizationListener listener = () -> fire();

	/**
	 * Constructor for the initialization of newly created
	 * {@code LocalizationProviderBridge} object.
	 * 
	 * @param provider localization provider
	 */
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		this.provider = provider;
	}

	/**
	 * Disconnects this localization provider.
	 */
	public void disconnect() {
		if (!connected)
			return;

		provider.removeLocalizationListener(listener);
		connected = false;
	}

	/**
	 * Connects this localization provider.
	 */
	public void connect() {
		if (connected)
			return;

		provider.addLocalizationListener(listener);
		connected = true;
	}

	@Override
	public String getString(String key) {
		return provider.getString(key);
	}

}
