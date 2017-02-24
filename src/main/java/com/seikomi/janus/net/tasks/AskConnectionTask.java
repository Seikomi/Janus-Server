package com.seikomi.janus.net.tasks;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seikomi.janus.net.JanusClient;

/**
 * Asking for connection task. Try to connect to a distant server to send and
 * receive commands.
 * 
 * @author Nicolas SYMPHORIEN (nicolas.symphorien@gmail.com)
 *
 */
public class AskConnectionTask extends JanusTask {
	static final Logger LOGGER = LoggerFactory.getLogger(AskConnectionTask.class);

	private Socket commandSocket;
	private DataInputStream in;
	private Scanner scanner;

	/**
	 * Construct a new connection task for a janus client.
	 * 
	 * @param client
	 *            the janus client
	 */
	public AskConnectionTask(JanusClient client) {
		super(client);
	}

	/**
	 * Established the connection with a janus server and open an input stream
	 * to send commands.
	 */
	@Override
	public void beforeLoop() {
		try {
			commandSocket = new Socket("localhost", ((JanusClient) networkApp).getCommandPort());
			in = new DataInputStream(commandSocket.getInputStream());
			scanner = new Scanner(System.in);
		} catch (IOException e) {
			LOGGER.error("An error occurs during connection establishment", e);
		}

	}

	/**
	 * Read the commands write on standart input and send it to the server.
	 * Then, display the result.
	 */
	@Override
	public void loop() {
		try {
			String message = in.readUTF();
			LOGGER.info(message);

			String command = scanner.next();

			DataOutputStream dataOutputStream = new DataOutputStream(commandSocket.getOutputStream());
			dataOutputStream.writeUTF(command);
			dataOutputStream.flush();

		} catch (IOException e) {
			LOGGER.error("An error occurs during connection establishment", e);
		}

	}

	@Override
	public void afterLoop() {
		// Nothing to do.
	}

	/**
	 * Restart the connection task.
	 */
	public void restart() {
		stop();
		run();
	}

	/**
	 * Stop the connection task.
	 */
	public void stop() {
		endLoop();
	}

}
