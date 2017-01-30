package com.seikomi.janus.services;

import com.seikomi.janus.net.JanusServer;
import com.seikomi.janus.net.tasks.DownloadTask;

/**
 * Download service to handle data transfert between the client and the server.
 * 
 * @author Nicolas SYMPHORIEN (nicolas.symphorien@gmail.com)
 *
 */
public final class DownloadService extends JanusService {

	/**
	 * Create a new instance of the download service by passing in arguments the
	 * targeted Janus server.
	 * 
	 * @param server
	 *            the targeted server
	 */
	public DownloadService(JanusServer server) {
		super(server);
	}

	/**
	 * Sends the files identified on the file system by the array of filenames.
	 * 
	 * @param fileNames
	 *            the array of filenames of files to send.
	 */
	public void send(String[] fileNames) {
		DownloadTask downloadTask = new DownloadTask(server, fileNames);
		Thread downloadThread = new Thread(downloadTask, "downloadThread");
		downloadThread.start();
	}

}
