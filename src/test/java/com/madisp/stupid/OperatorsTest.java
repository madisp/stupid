package com.madisp.stupid;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class OperatorsTest extends BaseExpressionTest {
	@Test
	public void testPlus() throws Exception {
		assertEquals(Integer.class, eval("2 + 3").getClass());
		assertEquals(2 + 3, eval("2 + 3"));
		assertEquals(Double.class, eval("2 + 3d").getClass());
		assertEquals(2 + 3d, (Double)eval("2 + 3d"), 0.0001d);
		assertEquals(2d + 3, (Double)eval("2d + 3"), 0.0001d);
		assertEquals(1.5d + 1.5d, (Double)eval("1.5 + 1.5d"), 0.0001d);
		assertEquals("foo" + "bar", eval("'foo' + 'bar'"));
		assertEquals("foo" + "bar" + "foobar", eval("'foo' + 'bar' + 'foobar'"));
		assertEquals("foo" + 1, eval("'foo' + 1"));
		assertEquals(1 + "foo", eval("1 + 'foo'"));
	}

	@Test
	public void testMinus() throws Exception {
		assertEquals(Integer.class, eval("2 - 3").getClass());
		assertEquals(2 - 3, eval("2 - 3"));
		assertEquals(Double.class, eval("2 - 3d").getClass());
		assertEquals(2 - 3d, (Double)eval("2 - 3d"), 0.0001d);
		assertEquals(2d - 3, (Double)eval("2d - 3"), 0.0001d);
		assertEquals(1.5d - 1.5d, (Double)eval("1.5 - 1.5d"), 0.0001d);
	}

	@Test
	public void testMultiplication() throws Exception {
		assertEquals(Integer.class, eval("2 * 3").getClass());
		assertEquals(2 * 3, eval("2 * 3"));
		assertEquals(Double.class, eval("2 * 3d").getClass());
		assertEquals(2 * 3d, (Double)eval("2 * 3d"), 0.0001d);
		assertEquals(2d * 3, (Double)eval("2d * 3"), 0.0001d);
	}

	@Test
	public void testDivision() throws Exception {
		assertEquals(Integer.class, eval("2 / 3").getClass());
		assertEquals(0, eval("2 / 3"));
		assertEquals(3, eval("7 / 2"));
		assertEquals(3, eval("9 / 3"));
		assertEquals(Double.class, eval("2 / 3d").getClass());
		assertEquals(2 / 3d, (Double)eval("2 / 3d"), 0.0001d);
		assertEquals(2 / 3d, (Double)eval("2d / 3"), 0.0001d);
	}

	@Test
	public void testNegation() throws Exception {
		assertEquals(Integer.class, eval("-2").getClass());
		assertEquals(-2, eval("-2"));
		assertEquals(Double.class, eval("-2d").getClass());
		assertEquals(-2d, (Double)eval("-2d"), 0.0001d);
		assertEquals(0, eval("-'asdf'")); // -(non-numeric) yields 0
	}

	@Test
	public void testArithmeticPrecedence() throws Exception {
		assertEquals(Integer.class, eval("3+5*2").getClass());
		assertEquals(3+5*2, eval("3+5*2"));
		assertEquals((3+5)*2, eval("(3+5)*2"));
	}

	@Test
	public void testAnd() throws Exception {
		assertEquals(Boolean.class, eval("false and false").getClass());
		assertEquals(false && false, eval("false and false"));
		assertEquals(false && true, eval("false and true"));
		assertEquals(true && false, eval("true and false"));
		assertEquals(true && true, eval("true and true"));
	}

	@Test
	public void testOr() throws Exception {
		assertEquals(Boolean.class, eval("false or false").getClass());
		assertEquals(false || false, eval("false or false"));
		assertEquals(false || true, eval("false or true"));
		assertEquals(true || false, eval("true or false"));
		assertEquals(true || true, eval("true or true"));
	}

	@Test
	public void testBooleanNegation() throws Exception {
		assertEquals(Boolean.class, eval("!false").getClass());
		assertEquals(!true, eval("!true"));
		assertEquals(!false, eval("!false"));
	}

	@Test
	public void testBooleanPrecedence() throws Exception {
		assertEquals(Boolean.class, eval("false and false or true").getClass());
		assertEquals(false && false || true, eval("false and false or true"));
		assertEquals(true || false && false, eval("true or false and false"));
		assertEquals((true || false) && false, eval("(true or false) and false"));
		assertEquals(false && (false || true), eval("false and (false or true)"));
		assertEquals((true || false) && !false, eval("(true or false) and !false"));
	}

	@Test
	public void testTernary() throws Exception {
		assertEquals(true ? false : true, eval("true ? false : true"));
		assertEquals(false ? false : true, eval("false ? false : true"));
	}
}
