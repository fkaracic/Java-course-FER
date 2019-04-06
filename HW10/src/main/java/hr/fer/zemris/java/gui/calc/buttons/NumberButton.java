package hr.fer.zemris.java.gui.calc.buttons;

import java.util.Objects;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.CalcModel;
import hr.fer.zemris.java.gui.calc.Calculator;

/**
 * Represents number button, i.e. buttons that contain decimal digits 0-9.
 * 
 * @author Filip Karacic
 *
 */
public class NumberButton extends JButton {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Digit of this button.
	 */
	private int number;

	/**
	 * Model of the calculator.
	 */
	private CalcModel model;

	/**
	 * COnstructor for the number button.
	 * 
	 * @param number digit of this button
	 * @param model model of the calculator
	 * 
	 * @throws IllegalArgumentException if the given number is not decimal digit
	 * @throws NullPointerException if the given model is <code>null</code>
	 */
	public NumberButton(int number, CalcModel model) {
		if (number < 0 || number > 9)
			throw new IllegalArgumentException("Number must be decimal digit.");

		this.number = number;
		this.model = Objects.requireNonNull(model);

		initializeButton();
	}

	/**
	 * Initializes this button.
	 */
	private void initializeButton() {

		addActionListener(l -> {
			performAction();
		});

		setBackground(Calculator.BUTTON_COLOR);
		setText(number + "");
	}

	/**
	 * Action of the button, performed when pressed.
	 */
	private void performAction() {
		if (Calculator.newNumber) {
			model.clear();
			Calculator.newNumber = false;
		}

		model.insertDigit(number);
	}

}
