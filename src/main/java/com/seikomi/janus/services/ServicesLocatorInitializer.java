package com.seikomi.janus.services;

import com.seikomi.janus.net.JanusServer;

public class ServicesLocatorInitializer {
	
	private ServicesLocatorInitializer() {
		// Hide public constructor.
	}
	
	public static void load(JanusServer server) {
		ServicesLocator.load(new DownloadService(server));
	}

}
