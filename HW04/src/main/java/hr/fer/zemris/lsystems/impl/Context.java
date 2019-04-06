package hr.fer.zemris.lsystems.impl;

import java.util.Objects;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Class {@code Context} enables execution of fracture display procedures and
 * offers stack providing methods for placing and retrieving turtle states.
 * 
 * @author Filip Karacic
 *
 */
public class Context {

	/**
	 * Stack on which turtle states are placed on or retrieved from.
	 */
	private ObjectStack stack;

	/**
	 * Initializes newly created {@code Context} object.
	 */
	public Context() {
		stack = new ObjectStack();
	}

	/**
	 * Returns last turtle state pushed without poping it.
	 * 
	 * @return last turtle state pushed
	 * @throws ContextNoElementsException
	 *             if there are no elements pushed to this context
	 */
	public TurtleState getCurrentState() {
		if (stack.isEmpty()) throw new ContextNoElementsException();
		
		return (TurtleState) stack.peek();
	}

	/**
	 * Pushes the given state.
	 * 
	 * @param state
	 *            turtle state to be pushed
	 * @throws NullPointerException
	 *             if the given state is {@code null}
	 */
	public void pushState(TurtleState state) {
		Objects.requireNonNull(state);
		
		stack.push(state);
	}

	/**
	 * Pops the last pushed turtle state if there is one.
	 * 
	 * @throws ContextNoElementsException
	 *             if there are no elements pushed to this context
	 */
	public void popState() {
		if (stack.isEmpty()) throw new ContextNoElementsException();
		
		stack.pop();
	}
}
