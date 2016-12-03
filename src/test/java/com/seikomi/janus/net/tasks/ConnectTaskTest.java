package com.seikomi.janus.net.tasks;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.seikomi.janus.net.JanusServer;
import com.seikomi.janus.net.properties.JanusServerProperties;
import com.seikomi.janus.net.properties.TestUtils;

public class ConnectTaskTest {

	private final static URL PROPERTIES_URL = TestUtils.getServerPropertiesURL();

	private JanusServer server;
	private JanusServerProperties serverProperties;

	@Before
	public void setUp() throws Exception {
		Path serverPropertiePath = Paths.get(PROPERTIES_URL.toURI());
		serverProperties = new JanusServerProperties(serverPropertiePath);
		server = new JanusServer(serverProperties);
		server.start();
	}

	@After
	public void tearDown() throws Exception {
		server.stop();

		serverProperties = null;
		server = null;
	}

	@Test
	public void testOneClientConnection() throws UnknownHostException, IOException {
		Socket clientSocket = new Socket(InetAddress.getLocalHost(), server.getCommandPort());
		boolean isConnected = clientSocket.isConnected() && clientSocket.isBound();

		assertTrue(isConnected);

		clientSocket.close();
	}
	
//	@Test
//	public void testMultiClientConnection() throws UnknownHostException, IOException, InterruptedException {
//		
//		final int NUMBER_OF_CLIENT = 5;
//		Socket[] clientsSockets = new Socket[NUMBER_OF_CLIENT];
//		for (Socket socket : clientsSockets) {
//			socket = new Socket(InetAddress.getLocalHost(), server.getCommandPort());
//			boolean isConnected = socket.isConnected() && socket.isBound();
//			assertTrue(isConnected);
//		}
//		for (Socket socket : clientsSockets) {
//			socket.close();
//		}
//	}

}
