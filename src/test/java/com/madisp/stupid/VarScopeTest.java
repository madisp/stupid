package com.madisp.stupid;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

public class VarScopeTest extends BaseExpressionTest {
	@Test
	public void testNoCreate() throws Exception {
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("foo", "foo");
		vars.put("bar", "bar");

		ctx.pushScope(new VarScope(vars));
		assertNotNull(eval("foo"));
		assertEquals("foo", eval("foo"));
		assertNotNull(eval("bar"));
		assertEquals("bar", eval("bar"));
		assertNull(eval("foobar"));
		assertEquals("foobar", eval("foo + bar"));

		ctx.popScope();
	}

	@Test
	public void testCreateOnSet() throws Exception {
		ctx.pushScope(new VarScope(VarScope.Type.CREATE_ON_SET));

		assertNull(eval("foo"));
		assertEquals("foo", eval("foo = 'foo'"));
		assertNull(eval("bar"));
		assertEquals("bar", eval("bar = 'bar'"));
		assertEquals("foobar", eval("foo + bar"));

		ctx.popScope();
	}

	@Test
	public void testNestedCreates() throws Exception {
		ctx.pushScope(new VarScope(VarScope.Type.CREATE_ON_SET_OR_GET));

		assertNull(eval("foo"));
		assertNull(eval("foo.bar"));
		assertEquals("bar", eval("foo.bar = 'bar'"));
		assertNull(eval("foo"));
		assertNotNull(eval("foo.bar"));
		assertNull(eval("bar"));
		assertEquals("bar", eval("foo.bar"));

		ctx.popScope();
	}

	@Test
	public void testDeeplyNestedCreates() throws Exception {
		ctx.pushScope(new VarScope(VarScope.Type.CREATE_ON_SET_OR_GET));

		assertNull(eval("foo"));
		assertNull(eval("foo.bar"));
		assertNull(eval("foo.bar.baz"));
		assertEquals("foobarbaz", eval("foo.bar.baz = 'foobarbaz'"));
		assertNull(eval("foo"));
		assertNull(eval("foo.bar"));
		assertNotNull(eval("foo.bar.baz"));
		assertEquals("foobarbaz", eval("foo.bar.baz"));

		ctx.popScope();
	}

	@Test
	public void testCreatesOnNonNulls() throws Exception {
		ctx.pushScope(new VarScope(VarScope.Type.CREATE_ON_SET_OR_GET));
		assertNull(eval("foo"));
		assertEquals("foo", eval("foo = 'foo'"));
		assertEquals("foo", eval("foo"));
		assertNull(eval("foo.bar"));
		assertEquals("bar", eval("foo.bar = 'bar'"));
		assertEquals("bar", eval("foo.bar"));
		assertEquals("foobar", eval("foo + foo.bar"));
		ctx.popScope();
	}
}