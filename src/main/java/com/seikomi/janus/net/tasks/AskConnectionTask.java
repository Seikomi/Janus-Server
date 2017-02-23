package com.seikomi.janus.net.tasks;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seikomi.janus.net.JanusClient;

public class AskConnectionTask extends JanusTask {
	static final Logger LOGGER = LoggerFactory.getLogger(AskConnectionTask.class);
	
	private Socket commandSocket;
	private DataInputStream in;
	private Scanner scanner;

	public AskConnectionTask(JanusClient client) {
		super(client);
	}

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

	public void restart() {
		stop();
		run();
	}

	public void stop() {
		endLoop();
	}


	

}
