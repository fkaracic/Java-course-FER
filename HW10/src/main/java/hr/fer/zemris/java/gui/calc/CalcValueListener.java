package hr.fer.zemris.java.gui.calc;

/**
 * Represents listener for the calculator.
 * 
 * @author Filip Karacic
 *
 */
public interface CalcValueListener {

	/**
	 * Notifies listener that the value has been changed.
	 * 
	 * @param model
	 *            model of the calculator
	 */
	void valueChanged(CalcModel model);
}