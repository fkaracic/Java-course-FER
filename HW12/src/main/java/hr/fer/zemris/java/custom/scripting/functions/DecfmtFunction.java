package hr.fer.zemris.java.custom.scripting.functions;

import java.text.DecimalFormat;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

/**
 * Represent decfmt function, i.e. decimal formatter function. Arguments are
 * retrieved from the stack.
 * 
 * @author Filip Karacic
 *
 */
public class DecfmtFunction extends Function {

	/**
	 * Initializes newly created decfmt function.
	 * 
	 * @param stack stack with the arguments
	 */
	public DecfmtFunction(Stack<Object> stack) {
		super(stack);
	}

	@Override
	public void execute() {
		String format = (String) stack.pop();
		Object x = stack.pop();

		String xx = new ValueWrapper(x).toString();

		DecimalFormat decimalFormat = new DecimalFormat(format);

		stack.push(decimalFormat.format(Double.parseDouble(xx)));

	}

}
