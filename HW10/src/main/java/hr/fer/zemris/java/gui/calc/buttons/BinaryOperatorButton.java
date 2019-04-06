package hr.fer.zemris.java.gui.calc.buttons;

import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.gui.calc.CalcModel;
import hr.fer.zemris.java.gui.calc.Calculator;

/**
 * Represents binary operator button. Performs operations such as +, -, *, /..
 * 
 * @author Filip Karacic
 *
 */
public class BinaryOperatorButton extends JButton {
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Function of this button.
	 */
	private DoubleBinaryOperator function;
	/**
	 * Model for this calculator.
	 */
	private CalcModel model;

	/**
	 * Initializes binary operator button.
	 * 
	 * @param symbol symbol of the operation
	 * @param function function of the operation
	 * @param model model of the calculator
	 */
	public BinaryOperatorButton(String symbol, DoubleBinaryOperator function, CalcModel model) {
		this.function = Objects.requireNonNull(function);
		this.model = Objects.requireNonNull(model);

		setText(symbol);
		setBackground(Calculator.BUTTON_COLOR);

		addActionListener(l -> {
			performAction();
		});
	}

	/**
	 * Action of the button, performed when pressed.
	 */
	private void performAction() {
		try {
			if (model.isActiveOperandSet()) {
				model.setValue(
						model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue()));
				model.setActiveOperand(model.getValue());
				model.setPendingBinaryOperation(function);
			} else {
				model.setActiveOperand(model.getValue());
				model.setPendingBinaryOperation(function);
			}

			Calculator.newNumber = true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this.getParent(), "Syntax error!", "Error.", JOptionPane.ERROR_MESSAGE);
		}

	}

}
