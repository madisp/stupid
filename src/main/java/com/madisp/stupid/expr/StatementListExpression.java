package com.madisp.stupid.expr;

import com.madisp.stupid.ExecContext;
import com.madisp.stupid.Value;

import java.util.Collections;
import java.util.List;

public class StatementListExpression implements Value {
	private final List<Value> statements;

	public StatementListExpression(List<Value> statements) {
		this.statements = Collections.unmodifiableList(statements);
	}

	@Override
	public Object value(ExecContext ctx) {
		Object ret = null;
		for (Value e : statements) {
			ret = e.value(ctx);
		}
		return ret;
	}
}
