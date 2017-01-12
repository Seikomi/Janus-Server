package com.seikomi.janus.commands;

import static org.junit.Assert.assertArrayEquals;

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

public class ExitTest {
	
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

	@Test
	public void testAddCommandWithNoArgs() {
		CommandsFactory.addCommand("#EXIT", new Exit(server));
		String[] returns = CommandsFactory.executeCommand("#EXIT");
		assertArrayEquals(new String[] { "#EXIT OK" }, returns);

	}

}
