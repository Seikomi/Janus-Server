package com.seikomi.janus.commands;

import static org.junit.Assert.assertArrayEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ExitTest {

	@Before
	public void setUp() throws Exception {
		// Nothing to set up
	}

	@After
	public void tearDown() throws Exception {
		// Nothing to tear down
	}

	@Test
	public void testAddCommandWithNoArgs() {
		String[] returns = CommandsFactory.executeCommand("#EXIT");
		assertArrayEquals(new String[] { "#EXIT OK" }, returns);

	}

}
