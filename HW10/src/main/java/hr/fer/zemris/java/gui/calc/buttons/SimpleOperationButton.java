package hr.fer.zemris.java.gui.calc.buttons;

import java.util.Objects;
import java.util.function.Consumer;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.CalcModel;
import hr.fer.zemris.java.gui.calc.Calculator;

/**
 * Represents simple operations as clr, res..
 * 
 * @author Filip Karacic
 *
 */
public class SimpleOperationButton extends JButton {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor that initialies this operation.
	 * 
	 * @param name name of the operation
	 * @param model model of the calculator
	 * @param consumer consumer that accepts the given value
	 */
	public SimpleOperationButton(String name, CalcModel model, Consumer<CalcModel> consumer) {
		setBackground(Calculator.BUTTON_COLOR);
		setText(Objects.requireNonNull(name));

		addActionListener(l -> {
			consumer.accept(Objects.requireNonNull(model));
		});
	}

}
