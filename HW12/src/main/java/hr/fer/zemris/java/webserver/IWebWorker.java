package hr.fer.zemris.java.webserver;

/**
 * Represents worker that performs specified action when requested.
 * 
 * @author Filip Karacic
 *
 */
public interface IWebWorker {
	/**
	 * When requested performs specified action according to the request.
	 * 
	 * @param context
	 *            context of the web server
	 * @throws Exception
	 *             if error processing request occurs
	 */
	public void processRequest(RequestContext context) throws Exception;
}