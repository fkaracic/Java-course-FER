package hr.fer.zemris.java.custom.scripting.functions;

import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Represents function used for setting value of temporary parameter within the
 * given name.
 * 
 * @author Filip Karacic
 *
 */
public class TparamSetFunction extends Function {

	/**
	 * Context of the web server
	 */
	private RequestContext requestContext;

	/**
	 * Initializes newly created temporary parameter set function.
	 * 
	 * @param stack
	 *            stack with the arguments
	 * @param requestContext
	 *            context of the web server
	 */
	public TparamSetFunction(Stack<Object> stack, RequestContext requestContext) {
		super(stack);

		this.requestContext = requestContext;
	}

	@Override
	public void execute() {
		String name = (String) stack.pop();
		String value = new ValueWrapper(stack.pop()).toString();

		requestContext.setTemporaryParameter(name, value);
	}

}
