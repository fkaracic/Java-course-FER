package hr.fer.zemris.java.gui.calc.buttons;

import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.gui.calc.CalcModel;
import hr.fer.zemris.java.gui.calc.Calculator;

/**
 * Represents button of equality. When pressed result is evaluated.
 * 
 * @author Filip Karacic
 *
 */
public class EqualsButton extends JButton {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * MOdel of the calculator.
	 */
	private CalcModel model;

	/**
	 * Constructor for the equality button.
	 * 
	 * @param model model of the calculator
	 */
	public EqualsButton(CalcModel model) {
		this.model = Objects.requireNonNull(model);

		setText("=");
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
				double result = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(),
						model.getValue());

				model.setValue(result);

				model.clearActiveOperand();
				model.setPendingBinaryOperation(null);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this.getParent(), "Syntax error!", "Error.", JOptionPane.ERROR_MESSAGE);
		}

	}

}
