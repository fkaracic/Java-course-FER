package hr.fer.zemris.java.custom.scripting.functions;

import java.util.Objects;
import java.util.Stack;

/**
 * Represents abstract echo tag function that works with the stack. Functions
 * derived from this class performs specific action. Arguments are reached from
 * the stack.
 * 
 * @author Filip Karacic
 *
 */
public abstract class Function {

	/**
	 * Stack used for reaching arguments.
	 */
	Stack<Object> stack;

	/**
	 * Initializes newly created object.
	 * 
	 * @param stack
	 *            stack with arguments
	 */
	public Function(Stack<Object> stack) {
		this.stack = Objects.requireNonNull(stack);
	}

	/**
	 * Performs action for this function. Stack is used for retrieving arguments and
	 * returning the result.
	 */
	public abstract void execute();
}
