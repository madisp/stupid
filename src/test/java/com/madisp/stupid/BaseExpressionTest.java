package com.madisp.stupid;

import org.junit.After;
import org.junit.Before;

public abstract class BaseExpressionTest {
	protected StackedExecContext ctx = new StackedExecContext();
	private ExpressionFactory builder = new ExpressionFactory();

	protected Object eval(String expr) {
		Value e = builder.parseExpression(expr);
		return ctx.dereference(e);
	}

	@Before
	public void setUp() throws Exception {
		ctx.pushExecContext(new ReflectionScope(this));
	}

	@After
	public void tearDown() throws Exception {
		ctx.popExecContext();
	}
}
