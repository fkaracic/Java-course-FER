package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents abstract localization provider and has the ability to register,
 * de-register and inform listeners about changes.
 * 
 * @author Filip Karacic
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {
	/**
	 * Listeners for this provider.
	 */
	List<ILocalizationListener> listeners;

	/**
	 * Initializes newly created provider.
	 */
	public AbstractLocalizationProvider() {
		listeners = new ArrayList<>();
	}

	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		listeners.add(l);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners.remove(l);

	}

	/**
	 * Notifies all registered listeners about changes.
	 */
	public void fire() {
		for (ILocalizationListener listener : listeners) {
			listener.localizationChanged();
		}
	}
}
