package com.madisp.stupid.expr;

import com.madisp.stupid.Block;
import com.madisp.stupid.ExecContext;
import com.madisp.stupid.Value;

import java.util.Arrays;

public class BlockExpression implements Value {
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
}
