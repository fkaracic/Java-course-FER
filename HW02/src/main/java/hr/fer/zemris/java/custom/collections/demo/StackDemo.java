package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Program {@code StackDemo} must receive only one argument from the command
 * line and the argument should represent an expression which should be
 * evaluated. Expression must be in a postfix representation. Each operator
 * takes two preceding numbers and replaces them with the operation result.
 * Program supports only +, -, /, * and % (remainder of integer division). All
 * operators work with and produce integer results.
 * 
 * @author Filip Karacic
 *
 */
public class StackDemo {

	/**
	 * Method that is called when program starts.
	 * 
	 * @param args
	 *            command line arguments represented as array of {@code String}
	 */
	public static void main(String[] args) {
		
		if (args.length != 1) {
			System.err.println("You must enter one argument from command line.");
			return;
		}
		
		String[] expression = args[0].split("\\s+");

		ObjectStack stack = new ObjectStack();

		for (int i = 0; i < expression.length; i++) {

			if (isSign(expression[i])) {
				try {
					int b = (Integer) stack.pop();
					int a = (Integer) stack.pop();

					switch (expression[i]) {
					case "+":
						stack.push(a + b);
						break;
					case "-":
						stack.push(a - b);
						break;
					case "/":
							stack.push(a / b);
						break;
					case "*":
						stack.push(a * b);
						break;
					case "%":
						stack.push(a % b);
						break;
					}
				} catch (EmptyStackException e) {
					System.err.println("Wrong expression.Was: '" + args[0] + "'.");
					return;
				} catch(ArithmeticException e) {
					System.err.println("Dividing by zero is not supported.");
					return;
				}
			} else {
				try {
					int a = Integer.parseInt(expression[i]);
					stack.push(a);
				} catch (NumberFormatException e) {
					System.err.println("Invalid expression. Was" + "'" + args[0] + "'.");
					return;
				}

			}
		}

			if (stack.size() == 1) {
				System.out.println("Expression evaluates to " + stack.pop() + ".");
			} else {
				System.err.println("Wrong expression.Was: '" + args[0] + "'.");
			}
	}

	/**
	 * Returns {@code true} if {@code String} is a valid sign (+, -, /, %), false
	 * otherwise.
	 * 
	 * @param s
	 *            {@code String} to be checked
	 * @return {@code true} if {@code String} is a valid sign
	 */
	private static boolean isSign(String s) {
		if (s == null || s.isEmpty())
			return false;

		switch (s) {
		case "+":
			break;
		case "-":
			break;
		case "/":
			break;
		case "*":
			break;
		case "%":
			break;
		default:
			return false;
		}

		return true;
	}
}
