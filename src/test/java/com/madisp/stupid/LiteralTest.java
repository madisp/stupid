package com.madisp.stupid;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class LiteralTest extends BaseExpressionTest {
	@Test
	public void testBooleans() throws Exception {
		assertEquals(Boolean.class, eval("true").getClass());
		assertEquals(Boolean.TRUE, eval("true"));
		assertEquals(Boolean.FALSE, eval("false"));
	}

	@Test
	public void testStrings() throws Exception {
		assertEquals(String.class, eval("'true'").getClass());
		assertEquals("test", eval("'test'"));
		assertEquals("String with\nnewlines", eval("'String with\\nnewlines'"));
		assertEquals("String with\ttabs", eval("'String with\\ttabs'"));
		assertEquals("Escaped 'string", eval("'Escaped \\'string'"));
		assertEquals("Strings with \" doublequotes", eval("'Strings with \" doublequotes'"));
		assertEquals("Escape \\ fun", eval("'Escape \\\\ fun'"));
		assertEquals("Escape \\' fun", eval("'Escape \\\\\\' fun'"));
	}

	@Test
	public void testIntegers() throws Exception {
		assertEquals(Integer.class, eval("0").getClass());
		assertEquals(Integer.valueOf(5), eval("5"));
	}

	@Test
	public void testDoubles() throws Exception {
		assertEquals(Double.class, eval("1.0").getClass());
		assertEquals(1.0d, ((Double)eval("1.0")).doubleValue(), 0.001d);
		assertEquals(2.5d, ((Double)eval("2.5f")).doubleValue(), 0.001d);
		assertEquals(2.5d, ((Double)eval("2.5d")).doubleValue(), 0.001d);
	}

	@Test
	public void testNull() throws Exception {
		assertNull(eval("null"));
	}
}
