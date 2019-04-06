package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

/**
 * Represents implementation of the model for the calculator.
 * 
 * @author Filip Karacic
 *
 */
public class CalcModelImpl implements CalcModel {
	/**
	 * Current number of this calculator.
	 */
	private String number;
	/**
	 * Active operand of this calculator.
	 */
	private String activeOperand;
	/**
	 * Pending operation of this calculator.
	 */
	private DoubleBinaryOperator pendingOperation;

	/**
	 * List of listeners for this model.
	 */
	private List<CalcValueListener> listeners = new ArrayList<>();

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		listeners.add(Objects.requireNonNull(l));
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners.remove(Objects.requireNonNull(l));

	}

	@Override
	public String toString() {
		if (number == null)
			return "0";

		if (number.contains(";")) {
			number = number.replace(";", "");

			if (number.endsWith(".0"))
				return number = number.replace(".0", "");
		}

		if (number.startsWith("0."))
			return number;

		return number = number.replaceFirst("^0+(?!$)", "");
	}

	@Override
	public double getValue() {
		return number == null ? 0 : Double.parseDouble(number);
	}

	@Override
	public void setValue(double value) {
		if (Double.isInfinite(value) || Double.isNaN(value)) {
			throw new IllegalArgumentException("Given value cannot be Infinite or NaN.");
		}

		if (number == null) {
			number = new String();
		}

		number = value + ";";

		notifyValueChanged();
	}

	@Override
	public void clear() {
		number = null;
		notifyValueChanged();
	}

	@Override
	public void clearAll() {
		number = null;
		activeOperand = null;
		pendingOperation = null;
		notifyValueChanged();
	}

	@Override
	public void swapSign() {

		if (number != null) {
			if (number.contains("-")) {
				number = number.replace("-", "");
			} else {
				number = "-" + number;
			}

			notifyValueChanged();
		}

	}

	@Override
	public void insertDecimalPoint() {
		if (number == null) {
			number = "0.";
		} else if (!number.contains(".")) {
			number += ".";
		} else {
			return;
		}

		notifyValueChanged();
	}

	@Override
	public void insertDigit(int digit) {
		if (number == null) {
			number = new String();
		}

		if (Double.parseDouble(number + digit) < Double.MAX_VALUE) {
			number += digit;
		}
		notifyValueChanged();
	}

	@Override
	public boolean isActiveOperandSet() {
		return activeOperand != null;
	}

	@Override
	public double getActiveOperand() {
		if (activeOperand == null)
			throw new IllegalStateException("The active operand is not set.");

		return Double.parseDouble(activeOperand);
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		if (this.activeOperand == null) {
			this.activeOperand = new String();
		}

		this.activeOperand = activeOperand + "";

		notifyValueChanged();
	}

	/**
	 * Notifies every listener that the value has been changed.
	 */
	private void notifyValueChanged() {
		for (CalcValueListener listener : listeners) {
			listener.valueChanged(this);
		}

	}

	@Override
	public void clearActiveOperand() {
		activeOperand = null;

		notifyValueChanged();
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		pendingOperation = op;

	}

}
