package com.madisp.stupid;

import com.madisp.stupid.context.ReflectionContext;
import com.madisp.stupid.context.StackContext;
import org.junit.After;
import org.junit.Before;

public abstract class BaseExpressionTest {
	protected StackContext ctx = new StackContext();
	private ExpressionFactory builder = new ExpressionFactory();

	protected Object eval(String expr) {
		Value e = builder.parseExpression(expr);
		return ctx.dereference(e);
	}

	@Before
	public void setUp() throws Exception {
		ctx.pushExecContext(new ReflectionContext(this));
	}

	@After
	public void tearDown() throws Exception {
		ctx.popExecContext();
	}
}
