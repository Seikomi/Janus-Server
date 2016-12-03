package com.seikomi.janus.commands;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.seikomi.janus.commands.CommandFactory;

public class CommandFactoryTest {

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
		final String commandTestName = "#CMD_TEST";
		final String[] commandTestReturns = new String[] { "TEST" };

		commandFactory.addCommand(commandTestName, new CommandInterface() {
			@Override
			public String[] apply(String[] args) {
				return commandTestReturns;
			}
		});

		String[] returns = commandFactory.executeCommand(commandTestName);
		assertArrayEquals(commandTestReturns, returns);

	}

	@Test
	public void testAddCommandWithArgs() {
		final String commandTestName = "#CMD_TEST2";
		final String arg1 = "hello";
		final String arg2 = "world";
		final String[] commandTestReturns = new String[] { "TEST2", arg1, arg2 };

		commandFactory.addCommand(commandTestName, new CommandInterface() {
			@Override
			public String[] apply(String[] args) {
				assertEquals(2, args.length);
				return new String[] { commandTestReturns[0], arg1, arg2 };
			}
		});

		String[] returns = commandFactory.executeCommand(commandTestName + " " + arg1 + " " + arg2);
		assertArrayEquals(commandTestReturns, returns);

	}

}
