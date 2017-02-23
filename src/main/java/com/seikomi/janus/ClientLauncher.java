package com.seikomi.janus;

import com.seikomi.janus.net.JanusClient;

public class ClientLauncher {
	
	private ClientLauncher() {
		// Hide the public constructor.
	}
	
	public static void main(String[] args) {
		JanusClient client = new JanusClient(null);
		client.start();
	}

}
