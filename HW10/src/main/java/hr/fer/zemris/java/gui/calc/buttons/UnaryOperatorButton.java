package hr.fer.zemris.java.gui.calc.buttons;

import java.util.Objects;
import java.util.function.UnaryOperator;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.gui.calc.CalcModel;
import hr.fer.zemris.java.gui.calc.Calculator;

/**
 * Represents unary operator button. Used to describe sin,cos, tan, ctg, ln,
 * log.
 * 
 * @author Filip Karacic
 *
 */
public class UnaryOperatorButton extends JButton {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Ordinary function.
	 */
	private UnaryOperator<Double> function;
	/**
	 * Inversive function.
	 */
	private UnaryOperator<Double> inversion;
	/**
	 * <code>true</code> if inversive function needs to be calculated,
	 * <code>false</code> otherwise.
	 */
	public static boolean inv;

	/**
	 * Model of the calculator.
	 */
	private CalcModel model;

	/**
	 * Initializes newly created object representing button of the calculator.
	 * 
	 * @param name
	 *            name of the operation
	 * @param function
	 *            ordinary function
	 * @param inversion
	 *            inversive function
	 * @param model
	 *            model of the calculator
	 */
	public UnaryOperatorButton(String name, UnaryOperator<Double> function, UnaryOperator<Double> inversion,
			CalcModel model) {
		this.function = Objects.requireNonNull(function);
		this.inversion = Objects.requireNonNull(inversion);
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
			if (inv) {
				model.setValue(inversion.apply(model.getValue()));
			} else {
				model.setValue(function.apply(model.getValue()));
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this.getParent(), "Syntax error!", "Error.", JOptionPane.ERROR_MESSAGE);
		}
	}

}
