package com.seikomi.janus.services;

import com.seikomi.janus.net.JanusServer;
import com.seikomi.janus.net.tasks.DownloadTask;

public final class DownloadService extends AbstractServices {

	protected DownloadService(JanusServer server) {
		super(server);
	}

	public void send(String[] fileNames) {
		DownloadTask downloadTask = new DownloadTask(server, fileNames);
		Thread downloadThread = new Thread(downloadTask, "downloadThread");
		downloadThread.start();
	}

}
