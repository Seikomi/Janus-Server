package com.seikomi.janus.commands;

import com.seikomi.janus.net.JanusServer;

/**
 * Exit command. Just return exit message interpret by the server like
 * interrupting flag.
 * 
 * @author Nicolas SYMPHORIEN (nicolas.symphorien@gmail.com)
 *
 */
public class Exit extends JanusCommand {

	/**
	 * Construct a new exit command associated with the server.
	 * 
	 * @param server
	 *            the server
	 */
	public Exit(JanusServer server) {
		super(server);
	}

	@Override
	public String[] apply(String[] args) {
		return new String[] { "#EXIT OK" };
	}

}
