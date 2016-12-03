package com.sekomi.janus.services;

import com.seikomi.janus.net.JanusServer;
import com.seikomi.janus.net.tasks.DownloadTask;

public final class DownloadService {

	private static JanusServer server;
	private static DownloadService instance;

	private DownloadService() {
		// Hide the public constructor
	}

	public static void loadTargedServer(JanusServer server) {
		DownloadService.server = server;
	}

	public static synchronized DownloadService getInstance() throws NoServerTargetFoundException {
		if (server == null) {
			throw new NoServerTargetFoundException();
		}
		if (instance == null) {
			instance = new DownloadService();
		}
		return instance;
	}

	public void send(String[] fileNames) {
		DownloadTask downloadTask = new DownloadTask(server, fileNames);
		Thread downloadThread = new Thread(downloadTask, "downloadThread");
		downloadThread.start();
	}

}
