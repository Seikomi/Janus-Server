package com.seikomi.janus;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seikomi.janus.net.JanusServer;
import com.seikomi.janus.net.properties.JanusServerProperties;

/**
 * Main class of Janus server.
 * 
 * @author Nicolas SYMPHORIEN (nicolas.symphorien@gmail.com)
 */
public class ServerLauncher {
	static final Logger LOGGER = LoggerFactory.getLogger(ServerLauncher.class);

	private ServerLauncher() {
		// Hide the public constructor.
	}

	/**
	 * Starts the Janus server with the {@code server.properties} file.
	 * 
	 * @param args
	 *            the arguments : not require
	 */
	public static void main(String[] args) {
		Path propertiesFilePah = Paths.get("server.properties");
		try {
			JanusServerProperties serverProperties = new JanusServerProperties(propertiesFilePah);
			JanusServer server = new JanusServerInDebug(serverProperties);
			server.start();
			LOGGER.info("Janus server started and listening on ports : " + server.getCommandPort() + " for commands and "
					+ server.getDataPort() + " for data.");
		} catch (IOException e) {
			LOGGER.error("An unknown error occurs during the reading of Janus server properties file", e);
		}
	}
}