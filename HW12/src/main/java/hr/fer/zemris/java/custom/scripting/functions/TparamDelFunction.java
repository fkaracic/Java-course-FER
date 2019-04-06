package hr.fer.zemris.java.custom.scripting.functions;

import java.util.Stack;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Represents function used for deleting temporary parameter for the given name.
 * 
 * @author Filip Karacic
 *
 */
public class TparamDelFunction extends Function {

	/**
	 * Context of the web server.
	 */
	private RequestContext requestContext;

	/**
	 * Constructor for the function of deleting the temporary parameter within the
	 * given name.
	 * 
	 * @param stack
	 *            stack with the arguments
	 * @param requestContext
	 *            context of the web server
	 */
	public TparamDelFunction(Stack<Object> stack, RequestContext requestContext) {
		super(stack);

		this.requestContext = requestContext;
	}

	@Override
	public void execute() {
		String name = (String) stack.pop();
		requestContext.removeTemporaryParameter(name);
	}

}
