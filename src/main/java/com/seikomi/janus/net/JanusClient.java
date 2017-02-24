package com.seikomi.janus.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seikomi.janus.net.properties.JanusClientProperties;
import com.seikomi.janus.net.tasks.AskConnectionTask;

/**
 * Implementation of a socket client. It provide basic access to a Janus Server.
 * 
 * @author Nicolas SYMPHORIEN (nicolas.symphorien@gmail.com)
 *
 */
public class JanusClient implements NetworkApp {
	private static final Logger LOGGER = LoggerFactory.getLogger(JanusClient.class);

	private JanusClientProperties clientProperties;
	private AskConnectionTask askConnectionTask;

	/**
	 * Create a new instance of Janus client and configure it with the
	 * {@code .properties} file pass in argument.
	 * 
	 * @param clientProperties
	 *            the client {@code .properties} file
	 */
	public JanusClient(JanusClientProperties clientProperties) {
		this.clientProperties = clientProperties;
	}

	/**
	 * Start the Janus client and ask for connection to the server.
	 */
	@Override
	public void start() {
		askConnectionTask = new AskConnectionTask(this);

		Thread askConnectionThread = new Thread(askConnectionTask, "AskConnectionThread");
		askConnectionThread.start();
	}

	/**
	 * Restart the Janus client.
	 */
	@Override
	public void restart() {
		if (askConnectionTask != null) {
			askConnectionTask.restart();
			LOGGER.info("Ask connection task has been restart.");
		} else {
			LOGGER.error("Ask connection task not running : start first before restart");
		}
	}

	/**
	 * Stop the Janus client, close all associated sockets.
	 */
	@Override
	public void stop() {
		if (askConnectionTask != null) {
			askConnectionTask.stop();
			LOGGER.info("Janus client has been stop.");
		} else {
			LOGGER.error("Ask connection not running : start first before stop");
		}
	}

	/**
	 * Gets the command port in the client properties files.
	 * 
	 * @return the command port
	 */
	public int getCommandPort() {
		return (clientProperties == null ? 3008 : clientProperties.getCommandPort());
	}

}
