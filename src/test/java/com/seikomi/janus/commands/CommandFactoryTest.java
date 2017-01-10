package com.seikomi.janus.commands;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.seikomi.janus.commands.CommandFactory;
import com.seikomi.janus.net.JanusServer;
import com.seikomi.janus.net.properties.JanusServerProperties;
import com.seikomi.janus.net.properties.TestUtils;

public class CommandFactoryTest {
	private final static URL PROPERTIES_URL = TestUtils.getServerPropertiesURL();

	private CommandFactory commandFactory;
	private JanusServer server;
	private JanusServerProperties serverProperties;

	@Before
	public void setUp() throws Exception {
		Path serverPropertiePath = Paths.get(PROPERTIES_URL.toURI());
		serverProperties = new JanusServerProperties(serverPropertiePath);
		server = new JanusServer(serverProperties);
		commandFactory = CommandFactory.init(server);
	}

	@After
	public void tearDown() throws Exception {
		commandFactory = null;
		server = null;
		serverProperties = null;
	}

	@Test
	public void testAddCommandWithNoArgs() {
		final String commandTestName = "#CMD_TEST";
		final String[] commandTestReturns = new String[] { "TEST" };

		commandFactory.addCommand(commandTestName, new AbstractCommand(server) {
			
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

		commandFactory.addCommand(commandTestName, new AbstractCommand(server) {
			
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
