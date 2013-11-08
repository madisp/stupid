package com.madisp.stupid;

import org.junit.After;
import org.junit.Before;

public abstract class BaseExpressionTest {
	protected DefaultExecContext ctx = new DefaultExecContext();
	private ExpressionFactory builder = new ExpressionFactory();

	protected Object eval(String expr) {
		Value e = builder.parseExpression(expr);
		return ctx.deref(e);
	}

	@Before
	public void setUp() throws Exception {
		ctx.pushScope(new ReflectionScope(this));
	}

	@After
	public void tearDown() throws Exception {
		ctx.popScope();
	}
}