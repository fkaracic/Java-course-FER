package hr.fer.zemris.java.gui.calc.buttons;

import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.gui.calc.CalcModel;
import hr.fer.zemris.java.gui.calc.Calculator;

/**
 * Represents binary operation that has inversive function.
 * 
 * @author Filip Karacic
 *
 */
public class BinaryInversingOperatorButton extends JButton {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <code>true</code> if the inv check box is checked, false otherwise.
	 */
	public static boolean inv;

	/**
	 * Inversive function.
	 */
	private DoubleBinaryOperator inversion;
	/**
	 * Ordinary function.
	 */
	private DoubleBinaryOperator function;
	/**
	 * Model of the calculator.
	 */
	private CalcModel model;

	/**
	 * Constructor for this binary operator button.
	 * 
	 * @param name name of the operation
	 * @param inversion inversive operation
	 * @param function function operation
	 * @param model model of the calculator
	 */
	public BinaryInversingOperatorButton(String name, DoubleBinaryOperator inversion, DoubleBinaryOperator function,
			CalcModel model) {
		this.inversion = Objects.requireNonNull(inversion);
		this.function = Objects.requireNonNull(function);
		this.model = Objects.requireNonNull(model);

		setBackground(Calculator.BUTTON_COLOR);
		setText(name);

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
					
				if (inv) {
					model.setPendingBinaryOperation(inversion);
				} else {
					model.setPendingBinaryOperation(function);
				}
				
				model.setActiveOperand(model.getValue());
			} else {
				model.setActiveOperand(model.getValue());
				if (inv) {
					model.setPendingBinaryOperation(inversion);
				} else {
					model.setPendingBinaryOperation(function);
				}
			}

			Calculator.newNumber = true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this.getParent(), "Syntax error!", "Error.", JOptionPane.ERROR_MESSAGE);

		}
	}

}
