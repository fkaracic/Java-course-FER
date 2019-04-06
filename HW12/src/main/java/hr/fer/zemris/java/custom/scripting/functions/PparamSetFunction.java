package hr.fer.zemris.java.custom.scripting.functions;

import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Represents persistent parameter setting function. Arguments are retrieved
 * from the stack and context of the web server is changed.
 * 
 * @author Filip Karacic
 *
 */
public class PparamSetFunction extends Function {

	/**
	 * Context of the web server.
	 */
	private RequestContext requestContext;

	/**
	 * Initializes newly created persistent parameter setting function.
	 * 
	 * @param stack stack with the arguments
	 * @param requestContext context of the web server
	 */
	public PparamSetFunction(Stack<Object> stack, RequestContext requestContext) {
		super(stack);

		this.requestContext = requestContext;
	}

	@Override
	public void execute() {
		String name = (String) stack.pop();
		String value = new ValueWrapper(stack.pop()).toString();

		requestContext.setPersistentParameter(name, value);
	}

}
