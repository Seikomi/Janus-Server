package com.seikomi.janus.net;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seikomi.janus.net.properties.JanusServerProperties;
import com.seikomi.janus.net.tasks.ConnectTask;

/**
 * Implementation of a socket server. With the usage of two distinct ports (like
 * an FTP server) : one for the commands and the other for the data transfer.
 * His configuration is store in a {@code .properties} file of type
 * {@link JanusServerProperties}.
 * 
 * @author Nicolas SYMPHORIEN (nicolas.symphorien@gmail.com)
 *
 */
public class JanusServer {
	static final Logger LOGGER = LoggerFactory.getLogger(JanusServer.class);

	private JanusServerProperties serverProperties;
	private ConnectTask connectTask;

	/**
	 * Create a new instance of Janus server and configure it with the
	 * {@code .properties} file pass in argument.
	 * 
	 * @param serverProperties
	 *            the server {@code .properties} file
	 */
	public JanusServer(JanusServerProperties serverProperties) {
		this.serverProperties = serverProperties;

	}

	/**
	 * Start the Janus server and wait for client connections.
	 */
	public void start() {
		try {
			connectTask = new ConnectTask(this);

			Thread connectTread = new Thread(connectTask, "ConnectThread");
			connectTread.start();
		} catch (IOException e) {
			LOGGER.error("An unknown error occurs during the starting of Janus server", e);
		}

	}

	/**
	 * Restart the Janus server.
	 */
	public void restart() {
		if (connectTask != null) {
			connectTask.restart();
		} else {
			LOGGER.error("Connect task not running : start first before restart");
		}
	}

	/**
	 * Stop the Janus server, close all associated sockets.
	 */
	public void stop() {
		if (connectTask != null) {
			connectTask.stop();
		} else {
			LOGGER.error("Connect task not running : start first before stop");
		}
	}

	/**
	 * Gets the command port store in {@code .properties} file.
	 * 
	 * @return the command port of this server
	 */
	public int getCommandPort() {
		return serverProperties.getCommandPort();
	}

	/**
	 * Gets the data port store in {@code .properties} file.
	 * 
	 * @return the data port of this server
	 */
	public int getDataPort() {
		return serverProperties.getDataPort();
	}
}
