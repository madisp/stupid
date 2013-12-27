package com.madisp.stupid;

/**
 * A Converter implements the type coercion rules for Stupid.
 * For a default (and pretty stupid) implementation, see
 * {@link com.madisp.stupid.context.DefaultConverter}.
 */
public interface Converter {
	/**
	 * Convert an object to a boolean.
	 * @param value object to convert, may be null
	 * @return boolean representation of the object
	 */
	boolean toBool(Object value);

	/**
	 * Convert an object to an integer.
	 * @param value object to convert, may be null
	 * @return integer representation of given object
	 */
	int toInt(Object value);

	/**
	 * Convert an object to a double.
	 * @param value object to convert, may be null
	 * @return the double representation of the argument
	 */
	double toDouble(Object value);

	/**
	 * Convert an object to a string.
	 * Java already has semantics for converting (.toString()) but
	 * it is nice to still have it here anyway.
	 * @param value object to convert, may be null
	 * @return the argument represented as a String
	 */
	String toString(Object value);
}
