package hr.fer.zemris.java.custom.scripting.functions;

import java.util.Stack;

/**
 * Represents function used for swapping top two objects on the stack so that
 * the second one is on the top of the stack and the first one is bellow it.
 * 
 * @author Filip Karacic
 *
 */
public class SwapFunction extends Function {

	/**
	 * Initializes newly created swap function.
	 * 
	 * @param stack
	 *            stack with arguments
	 */
	public SwapFunction(Stack<Object> stack) {
		super(stack);
	}

	@Override
	public void execute() {
		Object value1 = stack.pop();
		Object value2 = stack.pop();

		stack.push(value1);
		stack.push(value2);
	}

}
