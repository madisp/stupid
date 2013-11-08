package com.madisp.stupid;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

public class VariableTest extends BaseExpressionTest {
	public String a = "foo";
	public String b = "bar";
	public Foo foo = new Foo();

	@Test
	public void testThis() throws Exception {
		assertEquals(this.getClass(), eval("this").getClass());
		assertEquals(this, eval("this"));
	}

	@Test
	public void testSimpleFields() throws Exception {
		assertEquals("a".getClass(), eval("a").getClass());
		assertEquals(a, eval("a"));
		assertEquals(a + b, eval("a + b"));
	}

	@Test
	public void testMissingField() throws Exception {
		assertEquals(null, eval("no_such_field"));
	}

	@Test
	public void testNestedFields() throws Exception {
		assertEquals(foo.getClass(), eval("foo").getClass());
		assertEquals(foo.bar.getClass(), eval("foo.bar").getClass());
		assertEquals(foo.bar.hello.getClass(), eval("foo.bar.hello").getClass());
		assertEquals(foo.bar.hello, eval("foo.bar.hello"));
		assertEquals(this.foo.bar.hello, eval("this.foo.bar.hello"));
		assertEquals(((foo).bar).hello, eval("((foo).bar).hello"));
	}

	@Test
	public void testAssignment() throws Exception {
		assertEquals("bar", eval("b"));
		assertEquals("baz", eval("b = 'baz'"));
		assertEquals("baz", eval("b"));
		// restore b = bar
		b = "bar";
	}

	@Test
	public void testNestedAssignment() throws Exception {
		assertEquals("world", eval("foo.bar.hello"));
		assertEquals("wurld", eval("foo.bar.hello = 'wurld'"));
		assertEquals("wurld", eval("foo.bar.hello"));
		// resture foo.bar.hello = world
		foo.bar.hello = "world";
	}

	@Test
	public void testNullBase() throws Exception {
		// foo exists, but bar.foo doesn't
		assertNull(eval("bar"));
		assertNotNull(eval("foo"));
		assertNull(eval("bar.foo"));
	}

	public static class Foo {
		public Bar bar = new Bar();
	}

	public static class Bar {
		public String hello = "world";
	}
}
