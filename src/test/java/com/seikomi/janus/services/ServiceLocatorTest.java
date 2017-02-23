package com.seikomi.janus.services;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.seikomi.janus.JanusServerInDebug;
import com.seikomi.janus.net.JanusServer;
import com.seikomi.janus.net.JanusServerTest;
import com.seikomi.janus.net.properties.JanusServerProperties;

public class ServiceLocatorTest {
	private final static URL PROPERTIES_URL = JanusServerTest.class.getResource("serverTest.properties");

	private JanusServer server;
	private JanusServerProperties serverProperties;

	@Before
	public void setUp() throws Exception {
		Path serverPropertiePath = Paths.get(PROPERTIES_URL.toURI());
		serverProperties = new JanusServerProperties(serverPropertiePath);
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
		ServicesLocator.load(testService);

		assertEquals(testService, ServicesLocator.getService(ServiceForTest.class, server));
		assertEquals(testService, ServicesLocator.getService("ServiceForTest", server));
		assertEquals(testService,
				ServicesLocator.getService("ServiceForTest@" + Integer.toHexString(server.hashCode())));
	}

}
