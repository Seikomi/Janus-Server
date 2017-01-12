package com.seikomi.janus.net;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seikomi.janus.commands.CommandsFactory;
import com.seikomi.janus.commands.Download;
import com.seikomi.janus.commands.Exit;
import com.seikomi.janus.net.properties.JanusServerProperties;
import com.seikomi.janus.net.tasks.ConnectTask;
import com.seikomi.janus.services.DownloadService;
import com.seikomi.janus.services.Locator;

/**
 * Implementation of a socket server. With the usage of two distinct ports (like
 * an FTP server) : one for the commands and the other for the data transfer.
 * His configuration is store in a {@code .properties} file of type
 * {@link JanusServerProperties}.
 * 
 * @author Nicolas SYMPHORIEN (nicolas.symphorien@gmail.com)
 *
 */
public abstract class JanusServer {
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
		loadContext();
		loadJanusContext();

		try {
			connectTask = new ConnectTask(this);

			Thread connectTread = new Thread(connectTask, "ConnectThread");
			connectTread.start();
		} catch (IOException e) {
			LOGGER.error("An unknown error occurs during the starting of Janus server", e);
		}

	}

	/**
	 * Load defaults commands and services needed to run properly the Janus
	 * server.
	 */
	private void loadJanusContext() {
		CommandsFactory.addCommand("#EXIT", new Exit(this));
		CommandsFactory.addCommand("#DOWNLOAD", new Download(this));

		Locator.load(new DownloadService(this));
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
	 * This method must be override to load the context of the Janus server.
	 * Basically, it must be contain all commands and services you want to reach
	 * from any part of the application.
	 */
	protected abstract void loadContext();

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
