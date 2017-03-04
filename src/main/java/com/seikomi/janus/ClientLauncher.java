package com.seikomi.janus;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seikomi.janus.net.JanusClient;
import com.seikomi.janus.net.properties.JanusClientProperties;

/**
 * Main class of Janus client.
 * 
 * @author Nicolas SYMPHORIEN (nicolas.symphorien@gmail.com)
 */
public class ClientLauncher {
	static final Logger LOGGER = LoggerFactory.getLogger(ClientLauncher.class);

	private ClientLauncher() {
		// Hide the public constructor.
	}

	/**
	 * Starts the Janus client.
	 * 
	 * @param args
	 *            the arguments : not require
	 */
	public static void main(String[] args) {
		Path propertiesFilePah = Paths.get("client.properties");
		try {
			JanusClientProperties clientProperties = new JanusClientProperties(propertiesFilePah);
			JanusClient client = new JanusClient(clientProperties);
			client.start();
			LOGGER.info("Janus client started and connecting to the port " + client.getCommandPort() + " for commands");
		} catch (IOException e) {
			LOGGER.error("An unknown error occurs during the reading of Janus client properties file", e);
		}
	}

}
