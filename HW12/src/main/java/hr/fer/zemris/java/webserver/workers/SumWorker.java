package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Represents worker that produces sum of the two variables 'a' and 'b'. If any
 * of those variables is not set or is wrongly set(e.g. not number but string)
 * default values are set. Default value for variable 'a' is 1 and for 'b' is 2.
 * 
 * @author Filip Karacic
 *
 */
public class SumWorker implements IWebWorker {
	
	/**
	 * Integer number with the value one.
	 */
	private static final Integer ONE = Integer.valueOf(1);
	/**
	 * Integer number with the value two.
	 */
	private static final Integer TWO = Integer.valueOf(2);

	@Override
	public void processRequest(RequestContext context) {
		Integer a = null;
		Integer b = null;

		try {
			int parameterA = Integer.parseInt(context.getParameter("a"));
			a = parameterA;
		} catch (Exception e) {
		}

		try {
			int parameterB = Integer.parseInt(context.getParameter("b"));
			b = parameterB;
		} catch (Exception e) {
		}

		a = a == null ? ONE : a;
		b = b == null ? TWO : b;

		String result = Integer.toString(a + b);

		context.setTemporaryParameter("a", a.toString());
		context.setTemporaryParameter("b", b.toString());
		context.setTemporaryParameter("a+b", result);

		context.setMimeType("text/html");

		try {
			context.getDispatcher().dispatchRequest("/private/calc.smscr");
		} catch (Exception e) {
			throw new IllegalStateException(e);		}
	}
}