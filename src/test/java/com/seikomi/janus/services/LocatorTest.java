package com.seikomi.janus.services;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
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

public class LocatorTest {

	private JanusServer server;
	private JanusServerProperties serverProperties;

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	@Before
	public void setUp() throws Exception {
		Path serverPropertiesPath = Paths.get(temporaryFolder.getRoot().getPath() + "serverTest.properties");
		serverProperties = JanusPropertiesFileGenerator.createServerPropertiesFile(serverPropertiesPath);
		server = new JanusServerInDebug(serverProperties);

		testStart();
	}

	public void testStart() throws IOException {
		server.start();
	}

	@After
	public void tearDown() throws Exception {
		testStop();

		serverProperties = null;
		server = null;
	}

	public void testStop() {
		server.stop();
	}

	@Test
	public void testLoadService() {
		JanusService testService = new ServiceForTest(server);
		Locator.load(testService);

		assertEquals(testService, Locator.getService(ServiceForTest.class, server));
		assertEquals(testService, Locator.getService("ServiceForTest", server));
		assertEquals(testService,
				Locator.getService("ServiceForTest@" + Integer.toHexString(server.hashCode())));
		
		Locator.clear();
		
		assertEquals(null, Locator.getService(ServiceForTest.class, server));
	}
	
	public class ServiceForTest extends JanusService {

		public ServiceForTest(JanusServer server) {
			super(server);
		}

	}

}
