package com.seikomi.janus.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seikomi.janus.net.JanusServer;

/**
 * This is the abstract class of commands. If you want to create a new command
 * you must extend this class.
 * 
 * @author Nicolas SYMPHORIEN (nicolas.symphorien@gmail.com)
 *
 */
public abstract class JanusCommand {
	protected static final Logger LOGGER = LoggerFactory.getLogger(JanusCommand.class);
	protected JanusServer server;

	public JanusCommand(JanusServer server) {
		this.server = server;
	}

	/**
	 * Run the command with {@code args} like arguments.
	 * 
	 * @param args
	 *            the command arguments
	 * @return the result of the command like an array of strings.
	 */
	public abstract String[] apply(String[] args);
}
