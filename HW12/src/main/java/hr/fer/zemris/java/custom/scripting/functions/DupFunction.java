package hr.fer.zemris.java.custom.scripting.functions;

import java.util.Stack;

/**
 * Represents function that duplicates last value put on the stack so that copy
 * of that value is now on the top of the stack.
 * 
 * @author Filip Karacic
 *
 */
public class DupFunction extends Function {

	/**
	 * Initializes newly created dup function.
	 * 
	 * @param stack
	 *            stack with the argument
	 */
	public DupFunction(Stack<Object> stack) {
		super(stack);
	}

	@Override
	public void execute() {
		Object value = stack.peek();

		stack.push(value);
	}

}
