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

public class DownloadTest {
	
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
	public void testDownloadCommandWithNoArgs() {
		CommandsFactory.addCommand("#DOWNLOAD", new Download(server));
		String[] returns = CommandsFactory.executeCommand("#DOWNLOAD");
		assertArrayEquals(new String[] { "#DOWNLOAD NO FILES TO SEND" }, returns);

	}
	
	@Test
	public void testDownloadCommandWithArgs() {
		CommandsFactory.addCommand("#DOWNLOAD", new Download(server));
		String[] returns = CommandsFactory.executeCommand("#DOWNLOAD testFile");
		assertArrayEquals(new String[] { "#DOWNLOAD STARTED" }, returns);
		//TODO test transfert OK
	}

}
