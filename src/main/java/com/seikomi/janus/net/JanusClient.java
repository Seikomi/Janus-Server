package com.seikomi.janus.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seikomi.janus.net.properties.JanusClientProperties;
import com.seikomi.janus.net.tasks.AskConnectionTask;

public class JanusClient implements NetworkApp {
	private static final Logger LOGGER = LoggerFactory.getLogger(JanusClient.class);
	
	private JanusClientProperties clientProperties;
	private AskConnectionTask askConnectionTask;
	
	public JanusClient(JanusClientProperties clientProperties) {
		this.clientProperties = clientProperties;
	}
	
	/**
	 * Start the Janus client and ask for connection to the server.
	 */
	public void start() {
		askConnectionTask = new AskConnectionTask(this);
		
		Thread askConnectionThread = new Thread(askConnectionTask, "AskConnectionThread");
		askConnectionThread.start();
	}
	
	/**
	 * Restart the Janus client.
	 */
	public void restart() {
		// TODO
	}

	/**
	 * Stop the Janus client, close all associated sockets.
	 */
	public void stop() {
		// TODO
	}
	

}
