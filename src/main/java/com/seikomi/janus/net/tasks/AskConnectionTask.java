package com.seikomi.janus.net.tasks;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import com.seikomi.janus.net.JanusClient;

public class AskConnectionTask extends JanusTask {
	
	private Socket commandSocket;
	private DataInputStream in;
	private Scanner scanner;

	public AskConnectionTask(JanusClient client) {
		super(client);
	}

	@Override
	public void afterLoop() {
		// Nothing to do.
		
	}

	@Override
	public void beforeLoop() {
		try {
			commandSocket = new Socket("localhost", 3008);
			in = new DataInputStream(commandSocket.getInputStream());
			scanner = new Scanner(System.in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	@Override
	public void loop() {
		try {
			System.out.println(in.readUTF());
			String command = scanner.next();
			
			DataOutputStream dataOutputStream = new DataOutputStream(commandSocket.getOutputStream());
			dataOutputStream.writeUTF(command);
			dataOutputStream.flush();
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	

}
