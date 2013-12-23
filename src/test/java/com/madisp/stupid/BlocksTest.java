package com.madisp.stupid;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class BlocksTest extends BaseExpressionTest {
	public boolean run = false;

	public Runnable runnable = new Runnable() {
		@Override
		public void run() {
			run = true;
		}
	};

	public void run(Runnable run) {
		run.run();
	}

	@Test
	public void testSimpleBlock() throws Exception {
		assertNotNull(eval("{|x| x * x }"));
		assertEquals(Block.class, eval("{|x| x * x }").getClass());
		assertEquals(4, eval("{|x| x * x}.(2)"));
	}

	@Test
	public void testSingleMethodApply() throws Exception {
		assertEquals(false, run);
		run(runnable);
		assertEquals(true, run);
		run = false;

		assertEquals(false, run);
		eval("runnable.()");
		assertEquals(true, run);
		// restore run
		run = false;
	}

	// TODO commented out for now, I don't know if we want to have blocks to SAM's yet.
//	@Test
//	public void testBlockToSingleMethodInterface() throws Exception {
//		assertEquals(false, run);
//		eval("run(runnable)");
//		assertEquals(true, run);
//		run = false;
//
//		assertEquals(false, run);
//		eval("run({run = true})");
//		assertEquals(true, run);
//		run = false;
//	}
}
