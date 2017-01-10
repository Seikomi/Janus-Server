package com.seikomi.janus.services;

import com.seikomi.janus.net.JanusServer;

public abstract class AbstractServices {
	
	protected JanusServer server;
	
	protected AbstractServices(JanusServer server) {
//		ServicesLocator.load(this);
		this.server = server;
	}

}
