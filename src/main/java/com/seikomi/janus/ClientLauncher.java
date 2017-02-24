package com.seikomi.janus;

import com.seikomi.janus.net.JanusClient;

/**
 * Main class of Janus client.
 * 
 * @author Nicolas SYMPHORIEN (nicolas.symphorien@gmail.com)
 */
public class ClientLauncher {
	
	private ClientLauncher() {
		// Hide the public constructor.
	}
	
	/**
	 * Starts the Janus client.
	 * 
	 * @param args
	 *            the arguments : not require
	 */
	public static void main(String[] args) {
		JanusClient client = new JanusClient(null);
		client.start();
	}

}
