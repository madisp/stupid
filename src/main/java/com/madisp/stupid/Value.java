package com.madisp.stupid;

/**
 * Value is at the heart of stupid - it can be regarded as a future of sorts,
 * a value can be dereferenced given an {@link ExecContext}. All of the expressions
 * in stupid are Values.
 */
public interface Value {
	/**
	 * Dereference a value to a POJO instance
	 * @param ctx the execution context
	 * @return POJO representation of this value (expression)
	 */
	Object value(ExecContext ctx);
}
