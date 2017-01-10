package com.seikomi.janus.commands;

import com.seikomi.janus.net.JanusServer;
import com.seikomi.janus.services.DownloadService;
import com.seikomi.janus.services.ServicesLocator;

public class Download extends AbstractCommand {

	protected Download(JanusServer server) {
		super(server);
	}

	@Override
	public String[] apply(String[] args) {
		// TODO Auto-generated method stub
		DownloadService downloadService = ServicesLocator.getService(DownloadService.class, server);
		downloadService.send(args);
		
		return new String[] { "#DOWNLOAD STARTED" };
	}

}
