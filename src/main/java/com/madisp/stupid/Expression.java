package com.madisp.stupid;

/**
 * Expression is simply a {@link Value} with a (possibly empty)
 * set of sub-expressions. It is useful for analyzing the expression
 * tree outside of evaluation.
 */
public interface Expression<T> extends Value<T> {
	/**
	 * Get the set of sub-expressions, if any.
	 * @return an array of sub-expressions, it may be an empty array
	 */
	Expression[] children();
}
