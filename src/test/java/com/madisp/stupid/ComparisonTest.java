package com.madisp.stupid;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ComparisonTest extends BaseExpressionTest {
	@Test
	public void testLessThan() throws Exception {
		assertEquals(Boolean.class, eval("1 < 3").getClass());
		assertEquals(1 < 3, eval("1 < 3"));
		assertEquals(3 < 1, eval("3 < 1"));
	}

	@Test
	public void testLargerThan() throws Exception {
		assertEquals(Boolean.class, eval("1 > 3").getClass());
		assertEquals(1 > 3, eval("1 > 3"));
		assertEquals(3 > 1, eval("3 > 1"));
	}

	@Test
	public void testEquals() throws Exception {
		assertEquals(Boolean.class, eval("1 == 3").getClass());
		assertEquals(1 == 3, eval("1 == 3"));
		assertEquals(1 == 1, eval("1 == 1"));
		assertEquals(-1 == -1, eval("-1 == -1"));
	}
}
