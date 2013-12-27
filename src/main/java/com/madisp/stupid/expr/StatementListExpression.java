package com.madisp.stupid.expr;

import com.madisp.stupid.ExecContext;
import com.madisp.stupid.Expression;
import com.madisp.stupid.Value;

import java.util.Collections;
import java.util.List;

/**
 * A list of statements. Return value is the last one executed.
 * Allows one to construct intricate programs by having multiple
 * expressions.
 * In stupid: {@code statement1; statement2; ...; statementN}
 */
public class StatementListExpression implements Expression {
	private final List<Expression> statements;

	public StatementListExpression(List<Expression> statements) {
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

	@Override
	public Expression[] children() {
		return statements.toArray(new Expression[statements.size()]);
	}
}
