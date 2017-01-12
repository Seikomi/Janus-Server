package com.seikomi.janus.commands;

import com.seikomi.janus.net.JanusServer;
import com.seikomi.janus.services.DownloadService;
import com.seikomi.janus.services.Locator;

public class Download extends JanusCommand {

	public Download(JanusServer server) {
		super(server);
	}

	@Override
	public String[] apply(String[] args) {
		// TODO Auto-generated method stub
		DownloadService downloadService = Locator.getService(DownloadService.class, server);
		downloadService.send(args);
		
		return new String[] { "#DOWNLOAD STARTED" };
	}

}
