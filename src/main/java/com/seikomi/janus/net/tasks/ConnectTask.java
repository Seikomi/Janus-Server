package com.seikomi.janus.net.tasks;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seikomi.janus.net.JanusServer;

/**
 * Janus task that handle client connection, it is a one-thread-per-client
 * design. At the instantiation this task create a server socket on command
 * port. Then, when the task is run, wait for connection and each time a client
 * ask for connection, launch a treatment task.
 * 
 * @author Nicolas SYMPHORIEN (nicolas.symphorien@gmail.com)
 * 
 */
public class ConnectTask extends JanusTask {
	static final Logger LOGGER = LoggerFactory.getLogger(ConnectTask.class);

	private ServerSocket serverCommandSocket;
	private List<TreatmentTask> treatmentTasks;

	/**
	 * Construct a connect task associated with the Janus server in arguments.
	 * Create a server socket on command port.
	 * 
	 * @param server
	 *            the Janus server
	 * @throws IOException
	 *             if an I/O errors occurs
	 */
	public ConnectTask(JanusServer server) throws IOException {
		super(server);
		this.serverCommandSocket = new ServerSocket(server.getCommandPort());
		treatmentTasks = new ArrayList<>();
	}

	@Override
	protected void beforeLoop() {
		// Nothing to do
	}

	/**
	 * Wait for connection, and launch a treatment task at each client
	 * connection.
	 */
	@Override
	protected void loop() {
		try {
			LOGGER.info("Waiting for connect demande...");

			// The method blocks until a connection is made
			Socket commandSocket = serverCommandSocket.accept();
			LOGGER.info("One client is connect");

			TreatmentTask treatmentTask = new TreatmentTask(server, commandSocket);
			treatmentTasks.add(treatmentTask);
			Thread treatmentThread = new Thread(treatmentTask, "treatmentThread");
			treatmentThread.start();

		} catch (IOException e) {
			LOGGER.error("An error occurs during client connection", e);
		}
	}

	@Override
	protected void afterLoop() {
		// Nothing to do
	}

	/**
	 * Restarts all the treatments task associated with one client.
	 */
	public void restart() {
		stop();
		for (TreatmentTask treatmentTask : treatmentTasks) {
			treatmentTask.run();
		}
	}

	/**
	 * Stop all the treatments tasks.
	 */
	public void stop() {
		for (TreatmentTask treatmentTask : treatmentTasks) {
			treatmentTask.endLoop();
		}
	}

}