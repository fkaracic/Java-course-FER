package hr.fer.zemris.java.custom.scripting.functions;

import java.util.Stack;

import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Represents an action of setting mime type. Arguments are retrieved from the
 * stack. Changes the context of the web server.
 * 
 * @author Filip Karacic
 *
 */
public class SetMimeTypeFunction extends Function {

	/**
	 * Context of the web server.
	 */
	private RequestContext requestContext;

	/**
	 * Initializes newly created set mime type function object.
	 * 
	 * @param stack stack with the argument
	 * @param requestContext context of the web server
	 */
	public SetMimeTypeFunction(Stack<Object> stack, RequestContext requestContext) {
		super(stack);

		this.requestContext = requestContext;
	}

	@Override
	public void execute() {
		String mime = (String) stack.pop();

		requestContext.setMimeType(mime);
	}

}
