package com.seikomi.janus.commands;

import static org.junit.Assert.assertArrayEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.seikomi.janus.commands.CommandFactory;

public class ExitTest {

	private CommandFactory commandFactory;

	@Before
	public void setUp() throws Exception {
		commandFactory = CommandFactory.init();
	}

	@After
	public void tearDown() throws Exception {
		commandFactory = null;
	}

	@Test
	public void testAddCommandWithNoArgs() {
		String[] returns = commandFactory.executeCommand("#EXIT");
		assertArrayEquals(new String[] { "#EXIT OK" }, returns);

	}

}
