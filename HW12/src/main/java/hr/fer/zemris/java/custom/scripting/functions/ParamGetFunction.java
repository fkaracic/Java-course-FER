package hr.fer.zemris.java.custom.scripting.functions;

import java.util.Stack;

import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Represents function used for reaching parameter within the given
 * name.
 * 
 * @author Filip Karacic
 *
 */
public class ParamGetFunction extends Function {
	/**
	 * Context of the web server.
	 */
	private RequestContext requestContext;
	
	/**
	 * Initializes newly created parameter reaching function.
	 * 
	 * @param stack
	 *            stack with the arguments
	 * @param requestContext
	 *            context of the web server
	 */
	public ParamGetFunction(Stack<Object> stack, RequestContext requestContext) {
		super(stack);
		
		this.requestContext = requestContext;
	}

	@Override
	public void execute() {
		String defValue = (String) stack.pop();
		String name = (String) stack.pop();
		
		String value = requestContext.getParameter(name);
		
		stack.push(value == null ? defValue : value);
	}

}
