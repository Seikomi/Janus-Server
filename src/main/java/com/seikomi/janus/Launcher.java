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
public class Launcher {
	static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);
	
	private Launcher() {
		// Hide the public constructor.
	}
	
	/**
	 * Starts the Janus server with the {@code server.properties} file.
	 * 
	 * @param args the arguments : not require
	 */
	public static void main(String[] args) {
		Path propertiesFilePah = Paths.get("server.properties");
		try {
			JanusServerProperties serverProperties = new JanusServerProperties(propertiesFilePah);
			JanusServer server = new JanusServer(serverProperties);
			server.start();

		} catch (IOException e) {
			LOGGER.error("An unknown error occurs during the Janus server usage" , e);
		}
	}
}
