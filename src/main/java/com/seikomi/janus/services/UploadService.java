package com.seikomi.janus.services;

import com.seikomi.janus.net.JanusServer;
import com.seikomi.janus.net.tasks.DataTransfertTask;

/**
 * Upload service to handle data transfer between the client and the server.
 * 
 * @author Nicolas SYMPHORIEN (nicolas.symphorien@gmail.com)
 *
 */
public final class UploadService extends JanusService {

	/**
	 * Create a new instance of the upload service by passing in arguments the
	 * targeted Janus server.
	 * 
	 * @param server
	 *            the targeted server
	 */
	public UploadService(JanusServer server) {
		super(server);
	}

	/**
	 * Sends the files identified on the file system by the array of filenames.
	 * 
	 * @param fileNames
	 *            the array of filenames of files to send.
	 */
	public void receive(String[] fileNames) {
		DataTransfertTask uploadTask = new DataTransfertTask(server, fileNames, false);
		Thread dataTransfertThread = new Thread(uploadTask, "dataTransfertThread");
		dataTransfertThread.start();
	}

}
