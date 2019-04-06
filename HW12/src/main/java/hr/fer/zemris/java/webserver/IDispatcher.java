package hr.fer.zemris.java.webserver;

/**
 * Represents dispatcher that receives requests and whose job is to organize
 * further steps.
 * 
 * @author Filip Karacic
 *
 */
public interface IDispatcher {
	/**
	 * Receives request and process it producing result for the client.
	 * 
	 * @param urlPath
	 *            path requested
	 * @throws Exception
	 *             if error while processing request occurs
	 */
	void dispatchRequest(String urlPath) throws Exception;
}