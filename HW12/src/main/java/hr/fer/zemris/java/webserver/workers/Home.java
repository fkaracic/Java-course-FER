package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Represents worker that represents "home page". Searches for the background
 * color in the persistent parameters and if not found sets it to default.
 * 
 * @author Filip Karacic
 *
 */
public class Home implements IWebWorker {
	
	/**
	 * Default background color.
	 */
	private static final String DEFAULT_COLOR = "7F7F7F";

	@Override
	public void processRequest(RequestContext context) {
		String value = context.getPersistentParameter("bgcolor");
		value = value != null ? value : DEFAULT_COLOR;

		context.setTemporaryParameter("background", "#" + value);

		try {
			context.getDispatcher().dispatchRequest("/private/home.smscr");
		} catch (Exception e) {
			throw new IllegalStateException(e);		
		}
	}
}