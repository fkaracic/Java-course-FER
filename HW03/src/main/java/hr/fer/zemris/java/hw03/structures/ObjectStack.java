package hr.fer.zemris.java.hw03.structures;

import java.util.Objects;

/**
 * Represents stack that is implemented as {@code ArrayIndexedCollection}.
 * {@code ObjectStack} class has methods for work with the stack (e.g. push,
 * pop, peak, clear).
 * 
 * @author Filip Karacic
 *
 */
public class ObjectStack {
	/**
	 * Stack implemented as {@code ArrayIndexedCollection}.
	 */
	ArrayIndexedCollection stack;

	/**
	 * Initialize newly {@code ObjectStack} object that represents an empty stack.
	 */
	public ObjectStack() {
		stack = new ArrayIndexedCollection();
	}

	/**
	 * Returns {@code true} if stack contains no elements, false otherwise
	 * 
	 * @return {@code true} if stack contains no elements, false otherwise
	 */
	public boolean isEmpty() {
		return stack.size() == 0;
	}

	/**
	 * Returns size of this stack.
	 * 
	 * @return size of this stack
	 */
	public int size() {
		return stack.size();
	}

	/**
	 * Adds {@code value} onto the top of this stack. {@code null} 
	 * cannot be pushed.
	 * 
	 * @param value value to be added
	 * 
	 * @throws NullPointerException if value is {@code null}
	 */
	public void push(Object value) {
		Objects.requireNonNull(value);
		
		stack.add(value);
	}

	/**
	 * Removes the element at the top of this stack and returns it.
	 * 
	 * @return element at the top of this stack.
	 * 
	 * @throws EmptyStackException if this stack is empty
	 */
	public Object pop() {
		if (isEmpty()) throw new EmptyStackException("Cannot pop from an empty stack.");

		Object result = stack.get(size() - 1);
		stack.remove(size() - 1);

		return result;
	}

	/**
	 * Looks at the element at the top of this stack without removing it from the
	 * stack.
	 * 
	 * @return element at the top of this stack
	 * 
	 * @throws EmptyStackException if this stack is empty
	 */
	public Object peek() {
		if (isEmpty()) throw new EmptyStackException("Cannot peek at the top of an empty stack.");

		return stack.get(size() - 1);
	}

	/**
	 * Removes all of the elements from this stack. The stack is empty after this
	 * method returns.
	 */
	public void clear() {
		stack.clear();
	}

}