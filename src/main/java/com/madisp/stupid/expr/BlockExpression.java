package com.madisp.stupid.expr;

import com.madisp.stupid.Block;
import com.madisp.stupid.ExecContext;
import com.madisp.stupid.Expression;

import java.util.Arrays;

/**
 * An expression that returns a block when evaluated.
 * Usage in stupid: {@code sqr = {|x| x * x	}}
 */
public class BlockExpression implements Expression {
	private final StatementListExpression body;
	private final String[] varNames;

	public BlockExpression(String[] varNames, StatementListExpression body) {
		this.varNames = Arrays.copyOf(varNames, varNames.length);
		this.body = body;
	}

	@Override
	public Object value(final ExecContext ctx) {
		return new Block(varNames, body);
	}

	@Override
	public Expression[] children() {
		return new Expression[] { body };
	}
}
