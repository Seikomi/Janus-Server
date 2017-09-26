package com.seikomi.janus.commands;

import static org.junit.Assert.assertArrayEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.seikomi.janus.JanusServerInDebug;
import com.seikomi.janus.net.JanusServer;
import com.seikomi.janus.net.properties.JanusServerProperties;
import com.seikomi.janus.utils.JanusPropertiesFileGenerator;

public class ExitTest {

	private JanusServer server;
	private JanusServerProperties serverProperties;
	
	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	@Before
	public void setUp() throws Exception {
		Path serverPropertiesPath = Paths.get(temporaryFolder.getRoot().getPath() + "serverTest.properties");
		serverProperties = JanusPropertiesFileGenerator.createServerPropertiesFile(serverPropertiesPath);
		server = new JanusServerInDebug(serverProperties);
	}

	@After
	public void tearDown() throws Exception {
		CommandsFactory.clear();
		server = null;
		serverProperties = null;
	}

	@Test
	public void testExitCommandWithNoArgs() {
		CommandsFactory.addCommand(new Exit(), "#EXIT", server);
		String[] returns = CommandsFactory.executeCommand("#EXIT");
		assertArrayEquals(new String[] { "#EXIT OK" }, returns);

	}

}
