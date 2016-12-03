package com.seikomi.janus.commands;

/**
 * The command functional interface. If you want to create a new command you
 * must extend this interface.
 * 
 * @author Nicolas SYMPHORIEN (nicolas.symphorien@gmail.com)
 *
 */
@FunctionalInterface
interface CommandInterface {

	/**
	 * Run the command with {@code args} like arguments.
	 * 
	 * @param args
	 *            the command arguments
	 * @return the result of the command like an array of strings.
	 */
	public String[] apply(String[] args);
}
