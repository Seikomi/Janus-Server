package com.seikomi.janus.commands;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.seikomi.janus.JanusServerInDebug;
import com.seikomi.janus.net.JanusServer;
import com.seikomi.janus.net.properties.JanusServerProperties;
import com.seikomi.janus.net.properties.TestUtils;

public class CommandFactoryTest {
	private final static URL PROPERTIES_URL = TestUtils.getServerPropertiesURL();

	private JanusServer server;
	private JanusServerProperties serverProperties;

	@Before
	public void setUp() throws Exception {
		Path serverPropertiePath = Paths.get(PROPERTIES_URL.toURI());
		serverProperties = new JanusServerProperties(serverPropertiePath);
		server = new JanusServerInDebug(serverProperties);
	}

	@After
	public void tearDown() throws Exception {
		CommandsFactory.clear();
		server = null;
		serverProperties = null;
	}

	@Test(expected = InvocationTargetException.class)
	public void testPrivateConstructor() throws Exception {
		Constructor<CommandsFactory> constructor = CommandsFactory.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test
	public void testAddCommandWithNoArgs() {
		final String commandTestName = "#CMD_TEST";
		final String[] commandTestReturns = new String[] { "TEST" };

		CommandsFactory.addCommand(commandTestName, new JanusCommand(server) {

			@Override
			public String[] apply(String[] args) {
				return commandTestReturns;
			}
		});

		String[] returns = CommandsFactory.executeCommand(commandTestName);
		assertArrayEquals(commandTestReturns, returns);

	}

	@Test
	public void testAddCommandWithArgs() {
		final String commandTestName = "#CMD_TEST2";
		final String arg1 = "hello";
		final String arg2 = "world";
		final String[] commandTestReturns = new String[] { "TEST2", arg1, arg2 };

		CommandsFactory.addCommand(commandTestName, new JanusCommand(server) {

			@Override
			public String[] apply(String[] args) {
				assertEquals(2, args.length);
				return new String[] { commandTestReturns[0], arg1, arg2 };
			}
		});

		String[] returns = CommandsFactory.executeCommand(commandTestName + " " + arg1 + " " + arg2);
		assertArrayEquals(commandTestReturns, returns);

	}

}
