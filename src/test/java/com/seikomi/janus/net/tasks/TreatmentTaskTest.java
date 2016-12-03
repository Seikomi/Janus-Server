package com.seikomi.janus.net.tasks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
import com.seikomi.janus.net.tasks.TreatmentTask;

public class TreatmentTaskTest {

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
	public void testRecieveWelcomeMessageFromServer() throws UnknownHostException, IOException {
		Socket clientSocket = new Socket(InetAddress.getLocalHost(), server.getCommandPort());
		boolean isConnected = clientSocket.isConnected() && clientSocket.isBound();

		if (isConnected) {
			DataInputStream in = new DataInputStream(clientSocket.getInputStream());
			String serverResponce = in.readUTF();
			assertEquals(TreatmentTask.WELCOME_MESSAGE, serverResponce);
		}

		clientSocket.close();
	}

	@Test
	public void testSendMessageToServer() throws UnknownHostException, IOException {
		Socket clientSocket = new Socket(InetAddress.getLocalHost(), server.getCommandPort());
		boolean isConnected = clientSocket.isConnected() && clientSocket.isBound();

		if (isConnected) {
			DataInputStream in = new DataInputStream(clientSocket.getInputStream());
			String serverResponce = in.readUTF();
			assertEquals(TreatmentTask.WELCOME_MESSAGE, serverResponce);

			DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
			String request = "TEST";
			out.writeUTF(request);

			serverResponce = in.readUTF();
			assertNotNull(serverResponce);

		}

		clientSocket.close();
	}

}
