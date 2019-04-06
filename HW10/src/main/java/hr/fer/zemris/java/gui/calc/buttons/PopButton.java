package hr.fer.zemris.java.gui.calc.buttons;

import java.util.Objects;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.gui.calc.CalcModel;
import hr.fer.zemris.java.gui.calc.Calculator;

/**
 * Represents pop button for this calculator. Pops last number on the stack, if
 * there is one, and replaces the current number.
 * 
 * @author Filip Karacic
 *
 */
public class PopButton extends JButton {
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Model of the calculator.
	 */
	private CalcModel model;
	/**
	 * Stack for the calculator.
	 */
	private Stack<Double> stack;

	/**
	 * Constructor that initializes pop button.
	 * 
	 * @param model model of the calculator
	 * @param name name of the operation
	 * @param stack stack for the calculator
	 */
	public PopButton(CalcModel model, String name, Stack<Double> stack) {
		this.model = Objects.requireNonNull(model);
		this.stack = Objects.requireNonNull(stack);

		setBackground(Calculator.BUTTON_COLOR);
		setText(Objects.requireNonNull(name));

		addActionListener(l -> {
			performAction();
		});
	}

	/**
	 * Action of the button, performed when pressed.
	 */
	private void performAction() {
		try {
			model.setValue(stack.pop());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this.getParent(), "Empty stack!", "Error.", JOptionPane.ERROR_MESSAGE);
		}
	}

}
