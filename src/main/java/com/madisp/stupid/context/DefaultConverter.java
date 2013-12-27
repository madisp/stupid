package com.madisp.stupid.context;

import com.madisp.stupid.Converter;

/**
 * The default (and pretty stupid) type coercion rules for stupid.
 */
public class DefaultConverter implements Converter {

	/**
	 * Converts an object to bool.
	 * Returns true if the argument is not null and one of the following is true:
	 * <ol>
	 * <li>value is of type Boolean and value.booleanValue() returns true</li>
	 * <li>value is of type String and value.length() is larger than zero</li>
	 * <li>value is of type Integer and value.intValue() is not zero</li>
	 * <li>value is of type Long and value.intValue() is not zero</li>
	 * </ol>
	 * @param value object to convert, may be null
	 * @return boolean representation of the value
	 */
	@Override
	public boolean toBool(Object value) {
		if (value == null) {
			return false;
		} else if (value instanceof Boolean) {
			return ((Boolean) value).booleanValue();
		} else if (value instanceof CharSequence) {
			return ((CharSequence)value).length() > 0;
		} else if (value instanceof Integer) {
			return ((Integer)value).intValue() != 0;
		} else if (value instanceof Long) {
			return ((Long)value).longValue() != 0L;
		}
		return false;
	}

	/**
	 * Converts something to an integer.
	 * If the argument is of type Integer then the intValue is returned.
	 * If the argument is of type Float, Double or Long then it is truncated/converted
	 * to an int (with possible data loss in the process).
	 * In all other cases 0 is returned.
	 * @param value object to convert, may be null
	 * @return int representation of the value
	 */
	@Override
	public int toInt(Object value) {
		if (value instanceof Integer) {
			return ((Integer) value).intValue();
		} else if (value instanceof Double) {
			return ((Double)value).intValue();
		} else if (value instanceof Float) {
			return ((Float)value).intValue();
		} else if (value instanceof Long) {
			return ((Long)value).intValue();
		}
		return 0;
	}

	/**
	 * Converts the argument to a double-precision value.
	 * If the argument is of type Double then the double is returned.
	 * If the argument is of type Float, Integer or Long then it is truncated/converted
	 * to a double (with possible data loss in the process).
	 * In all other cases 0.0d is returned.
	 * @param value object to convert, may be null
	 * @return double representation of the value
	 */
	@Override
	public double toDouble(Object value) {
		if (value instanceof Double) {
			return ((Double) value).doubleValue();
		} else if (value instanceof Integer) {
			return ((Integer)value).doubleValue();
		} else if (value instanceof Long) {
			return ((Long)value).doubleValue();
		} else if (value instanceof Float) {
			return ((Float)value).doubleValue();
		}
		return 0.0d;
	}

	/**
	 * Returns the string representation of an object
	 * @param value object to convert, may be null
	 * @return "null" if value is null, value.toString() otherwise
	 */
	@Override
	public String toString(Object value) {
		return value == null ? "null" : value.toString();
	}
}
