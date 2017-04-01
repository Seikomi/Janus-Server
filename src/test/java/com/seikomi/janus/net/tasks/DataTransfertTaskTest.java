package com.seikomi.janus.net.tasks;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.seikomi.janus.JanusServerInDebug;
import com.seikomi.janus.net.JanusClient;
import com.seikomi.janus.net.JanusServer;
import com.seikomi.janus.net.properties.JanusClientProperties;
import com.seikomi.janus.net.properties.JanusProperties;
import com.seikomi.janus.net.properties.JanusServerProperties;
import com.seikomi.janus.utils.JanusPropertiesFileGenerator;

public class DataTransfertTaskTest {

	private JanusServer server;
	private JanusServerProperties serverProperties;

	private JanusClient client;
	private JanusClientProperties clientProperties;

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	@Before
	public void setUp() throws Exception {
		Path serverPropertiesPath = Paths.get(temporaryFolder.getRoot().getPath() + "serverTest.properties");
		Path clientPropertiesPath = Paths.get(temporaryFolder.getRoot().getPath() + "clientTest.properties");
		JanusProperties[] propertiesFiles = JanusPropertiesFileGenerator.createJanusProperties(serverPropertiesPath,
				clientPropertiesPath);

		serverProperties = (JanusServerProperties) propertiesFiles[0];
		server = new JanusServerInDebug(serverProperties);

		clientProperties = (JanusClientProperties) propertiesFiles[1];
		client = new JanusClient(clientProperties);

		server.start();	
		client.start();
	}

	@After
	public void tearDown() throws Exception {
		client.stop();
		server.stop();		
		
	}

	@Test
	public void testDownloadTransfertBeetweenClientAndServer() throws InterruptedException {
		client.executeCommand("#DOWNLOAD LICENSE");
	}
	
	@Test
	public void testUploadTransfertBeetweenClientAndServer() throws InterruptedException {
		client.executeCommand("#UPLOAD LICENSE");
	}

}
