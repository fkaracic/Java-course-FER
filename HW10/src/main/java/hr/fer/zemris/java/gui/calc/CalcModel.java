package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;

/**
 * Represents model of the calculator.
 * 
 * @author Filip Karacic
 *
 */
public interface CalcModel {

	/**
	 * Adds the given listener for this model.
	 * 
	 * @param l
	 *            listener for the calculator
	 */
	void addCalcValueListener(CalcValueListener l);

	/**
	 * Removes the given listener for this model if existing.
	 * 
	 * @param l
	 *            listener for the calculator
	 */
	void removeCalcValueListener(CalcValueListener l);

	@Override
	String toString();

	/**
	 * Returns current value of the calculator.
	 * 
	 * @return current value of the calculator
	 */
	double getValue();

	/**
	 * Sets value of the calculator to the given value.
	 * 
	 * @param value
	 *            value for this calculator to be set
	 */
	void setValue(double value);

	/**
	 * Clears only current number. Everything else will not be changed.
	 */
	void clear();

	/**
	 * Resets calculator. Disregards state of the calculator.
	 */
	void clearAll();

	/**
	 * Swaps sign of the current number. Positive number becomes negative, and
	 * negative becomes positive.
	 */
	void swapSign();

	/**
	 * Inserts decimal point to the current number if not previously existing.
	 */
	void insertDecimalPoint();

	/**
	 * Inserts digit to the current number of the calculator.
	 * 
	 * @param digit digit to be inserted
	 */
	void insertDigit(int digit);

	/**
	 * Returns <code>true</code> if active operand is set, <code>false</code> otherwise.
	 * 
	 * @return <code>true</code> if active operand is set, <code>false</code> otherwise
	 */
	boolean isActiveOperandSet();

	/**
	 * Returns active operand if is set.
	 * 
	 * @return active operand if is set
	 * 
	 */
	double getActiveOperand();

	/**
	 * Sets active operand to the given value.
	 * 
	 * @param activeOperand value to be set
	 */
	void setActiveOperand(double activeOperand);

	/**
	 * Clears only active operand. Nothing else is changed.
	 */
	void clearActiveOperand();

	/**
	 * Returns pending operation for the given input.
	 * 
	 * @return pending operation for the given input
	 */
	DoubleBinaryOperator getPendingBinaryOperation();

	/**
	 * Sets pending operation of the calculator to the given value.
	 * 
	 * @param op operation to be set
	 */
	void setPendingBinaryOperation(DoubleBinaryOperator op);
}