package com.madisp.stupid;

/**
 * Value is at the heart of stupid - it can be regarded as a future of sorts,
 * a value can be dereferenced given an {@link ExecContext}. All of the expressions
 * in stupid are Values.
 */
public interface Value<T> {
	/**
	 * Dereference a value to a POJO instance
	 * @param ctx the execution context
	 * @return POJO representation of this value (expression)
	 */
	T value(ExecContext ctx);
}
