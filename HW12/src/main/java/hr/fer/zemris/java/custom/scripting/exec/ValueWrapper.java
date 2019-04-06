package hr.fer.zemris.java.custom.scripting.exec;

/**
 * {@code ValueWrapper} represents a wrapper for any value, including
 * <code>null</code>.
 * <p>
 * 
 * Arithmetic methods and comparison method is supported only for
 * <code>null</code> and instances of {@code Integer}, {@code Double} or
 * {@code String}. Supported arithmetic operations are: Addition, subtraction,
 * multiplication and division (dividing by zero is not permitted). There is
 * also comparison operation.
 * <p>
 * 
 * Value <code>null</code> is used as instance of {@code Integer} with zero
 * value. Value that is instance of {@code String} must be a representation of
 * double(scientific notation also supported) or integer number.
 * 
 * @author Filip Karacic
 *
 */
public class ValueWrapper {
	/**
	 * Value of this wrapper.
	 */
	private Object value;

	/**
	 * Initializes newly created wrapper.
	 * 
	 * @param value
	 *            value for this wrapper.
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}

	/**
	 * Performs addition of this value with the given value and updates this value
	 * to the result.
	 * 
	 * @param incValue
	 *            value to be added to this value
	 * @throws IllegalArgumentException
	 *             if any of the two arguments are not valid (i.e. if they are not
	 *             <code>null</code> or instances of {@code Integer},
	 *             {@code Double}, or {code String}
	 **/
	public void add(Object incValue) {
		Object result = calculate(value, incValue, OperationTypes.ADD);

		if (result == null)
			throw new IllegalArgumentException("Invalid type of operands.");

		value = result;
	}

	/**
	 * Performs subtraction of this value with the given value and updates this
	 * value to the result.
	 * 
	 * @param decValue
	 *            value to be subtracted from this value
	 * @throws IllegalArgumentException
	 *             if any of the two arguments are not valid (i.e. if they are not
	 *             <code>null</code> or instances of {@code Integer},
	 *             {@code Double}, or {code String}
	 */
	public void subtract(Object decValue) {
		Object result = calculate(value, decValue, OperationTypes.SUB);

		if (result == null)
			throw new IllegalArgumentException("Invalid type of operands.");

		value = result;
	}

	/**
	 * Performs multiplication of this value with the given value and updates this
	 * value to the result.
	 * 
	 * @param mulValue
	 *            value to be multiplied with this value
	 * @throws IllegalArgumentException
	 *             if any of the two arguments are not valid (i.e. if they are not
	 *             <code>null</code> or instances of {@code Integer},
	 *             {@code Double}, or {code String}
	 */
	public void multiply(Object mulValue) {
		Object result = calculate(value, mulValue, OperationTypes.MUL);

		if (result == null)
			throw new IllegalArgumentException("Invalid type of operands.");

		value = result;
	}

	/**
	 * Performs division of this value with the given value and updates this value
	 * to the result. Dividing by zero is not permitted.
	 * 
	 * @param divValue
	 *            value to be divided from this value
	 * @throws IllegalArgumentException
	 *             if any of the two arguments are not valid (i.e. if they are not
	 *             <code>null</code> or instances of {@code Integer},
	 *             {@code Double}, or {code String}
	 * @throws ArithmeticException
	 *             if dividing by zero is requested (i.e. if the given value is 0)
	 */
	public void divide(Object divValue) {
		Object result = calculate(value, divValue, OperationTypes.DIV);

		if (result == null)
			throw new IllegalArgumentException("Invalid type of operands.");

		value = result;
	}

	/**
	 * Performs the comparison of this value with the given value. Returns the value
	 * 0 if this value is equal to the argument, a value less than 0 if this value
	 * is numerically less than the argument, and a value greater than 0 if this
	 * value is numerically greater than the argument.
	 * 
	 * @param withValue
	 *            value to compare
	 * @return value 0 if equals, a value less than 0 if this value is less than the
	 *         argument, and a value greater than 0 if this value is greater than
	 *         the argument
	 * 
	 * @throws IllegalArgumentException
	 *             if any of the two arguments are not valid (i.e. if they are not
	 *             <code>null</code> or instances of {@code Integer},
	 *             {@code Double}, or {code String}
	 */
	public int numCompare(Object withValue) {
		Object result = calculate(value, withValue, OperationTypes.COMP);

		if (result == null)
			throw new IllegalArgumentException("Invalid type of operands.");

		return ((Integer) result).intValue();
	}

	/**
	 * Performs the operation defined with the {@code type}.
	 * 
	 * @param value1
	 *            the first value
	 * @param value2
	 *            the second value
	 * @param type
	 *            type of the operation
	 * @return result of the operation as an {@code Object} object
	 */
	private Object calculate(Object value1, Object value2, OperationTypes type) {
		Object operand1 = setOperand(value1);
		Object operand2 = setOperand(value2);

		if (operand1 == null || operand2 == null)
			return null;

		switch (type) {
		case ADD:
			return add(operand1, operand2);
		case SUB:
			return subtract(operand1, operand2);
		case MUL:
			return multiply(operand1, operand2);
		case DIV:
			return divide(operand1, operand2);
		case COMP:
			return compare(operand1, operand2);
		}

		return null;
	}

	/**
	 * Transforms the given values to integer or double operands, or returns
	 * <code>null</code> if the given value is not valid.
	 * 
	 * @param value
	 *            value to transform
	 * @return object representing double or integer number as {@code Object}
	 *         object.
	 */
	private Object setOperand(Object value) {
		if (value == null)
			return Integer.valueOf(0);

		if (value.getClass() == Integer.class || value.getClass() == Double.class)
			return value;

		if (value.getClass() == String.class) {
			String string = (String) value;

			if (string.contains(".") || string.contains("E")) {
				try {
					return Double.parseDouble(string);
				} catch (NumberFormatException e) {
				}
			} else {
				try {
					return Integer.parseInt(string);
				} catch (NumberFormatException e) {
				}
			}
		}

		return null;
	}

	/**
	 * Performs addition of the two given operands.
	 * 
	 * @param operand1
	 *            the first operand
	 * @param operand2
	 *            the second operand
	 * @return result of the operation
	 */
	private Object add(Object operand1, Object operand2) {
		if (operand1.getClass() == Integer.class && operand2.getClass() == Integer.class) {
			return value = Integer.valueOf((Integer) operand1 + (Integer) operand2);
		} else {
			return value = Double.valueOf(((Number) operand1).doubleValue() + ((Number) operand2).doubleValue());
		}
	}

	/**
	 * Performs subtraction of the two given operands.
	 * 
	 * @param operand1
	 *            the first operand
	 * @param operand2
	 *            the second operand
	 * @return result of the operation
	 */
	private Object subtract(Object operand1, Object operand2) {
		if (operand1.getClass() == Integer.class && operand2.getClass() == Integer.class) {
			return value = Integer.valueOf((Integer) operand1 - (Integer) operand2);
		} else {
			return value = Double.valueOf(((Number) operand1).doubleValue() - ((Number) operand2).doubleValue());
		}
	}

	/**
	 * Performs multiplication of the two given operands.
	 * 
	 * @param operand1
	 *            the first operand
	 * @param operand2
	 *            the second operand
	 * @return result of the operation
	 */
	private Object multiply(Object operand1, Object operand2) {
		if (operand1.getClass() == Integer.class && operand2.getClass() == Integer.class) {
			return value = Integer.valueOf((Integer) operand1 * (Integer) operand2);
		} else {
			return value = Double.valueOf(((Number) operand1).doubleValue() * ((Number) operand2).doubleValue());
		}
	}

	/**
	 * Performs division of the two given operands.
	 * 
	 * @param operand1
	 *            the first operand
	 * @param operand2
	 *            the second operand
	 * @return result of the operation
	 * 
	 * @throws ArithmeticException
	 *             if the zero division is requested
	 */
	private Object divide(Object operand1, Object operand2) {
		if (((Number) operand2).doubleValue() == 0)
			throw new ArithmeticException("Zero division is not supported.");

		if (operand1.getClass() == Integer.class && operand2.getClass() == Integer.class) {
			return value = Integer.valueOf((Integer) operand1 / (Integer) operand2);
		} else {
			return value = Double.valueOf(((Number) operand1).doubleValue() / ((Number) operand2).doubleValue());
		}
	}

	/**
	 * Performs comparison of the two given operands.
	 * 
	 * @param operand1
	 *            the first operand
	 * @param operand2
	 *            the second operand
	 * @return result of the operation(value 0 if equals, value greater than 0 if
	 *         the first operand is greater than the second, value less than 0
	 *         otherwise
	 */
	private Object compare(Object operand1, Object operand2) {
		if (operand1.getClass() == Integer.class && operand2.getClass() == Integer.class) {
			return Integer.compare((Integer) operand1, (Integer) operand2);
		} else {
			return Double.compare(((Number) operand1).doubleValue(), ((Number) operand2).doubleValue());
		}
	}

	/**
	 * Returns value of this wrapper.
	 * 
	 * @return value of this wrapper.
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Updates the value of this wrapper.
	 * 
	 * @param value
	 *            new value for this wrapper.
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		Object value = setOperand(this.value);
		
		if(value instanceof Integer) return ((Integer)value).toString();
		else if(value instanceof Double) return ((Double)value).toString();
		else throw new IllegalArgumentException();
	}

}
