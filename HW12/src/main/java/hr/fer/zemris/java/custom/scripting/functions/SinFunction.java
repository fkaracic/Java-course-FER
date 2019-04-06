package hr.fer.zemris.java.custom.scripting.functions;

import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

/**
 * Represents sine function. Argument is interpreted in degrees.
 * 
 * @author Filip Karacic
 *
 */
public class SinFunction extends Function {

	/**
	 * Initializes newly created sine function object.
	 * 
	 * @param stack stack with the argument
	 */
	public SinFunction(Stack<Object> stack) {
		super(stack);
	}

	@Override
	public void execute() {
		ValueWrapper value = new ValueWrapper(stack.pop());
		double degrees = Double.parseDouble(value.toString());
		double radians = degrees * Math.PI / 180;
		double result = Math.sin(radians);
		
		stack.push(new ValueWrapper(result).getValue());
	}
}
