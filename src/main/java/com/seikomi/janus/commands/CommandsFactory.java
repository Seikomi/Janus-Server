package com.seikomi.janus.commands;

import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * This class is the factory of commands use by Janus server. She stores a set
 * of commands and execute the command ask by a client. This factory <b>need<b/>
 * to be initialize before use it by the call of {@code init()} method.
 * TODO Updata JavaDoc comments
 * 
 * @author Nicolas SYMPHORIEN (nicolas.symphorien@gmail.com)
 *
 */
public class CommandsFactory {
	private final static HashMap<String, JanusCommand> commands = new HashMap<>();

	private CommandsFactory() {
		// Hide the public constructor
	}
	
	/**
	 * Add an external command in the command factory.
	 * 
	 * @param name
	 *            the associated name of the command
	 * @param command
	 *            the command
	 */
	public static void addCommand(String name, JanusCommand command) {
		commands.put(name, command);
	}

	/**
	 * Execute the command of name equals to the {@code commandString} pass in
	 * arguments.
	 * 
	 * @param commandString
	 *            the command string name
	 * @return the result of the command like an array of strings
	 */
	public static String[] executeCommand(String commandString) {
		String[] commandResult = null;

		StringTokenizer stringTokenizer = new StringTokenizer(commandString);
		String commandHeader = stringTokenizer.nextToken();
		if (commands.containsKey(commandHeader)) {
			if (stringTokenizer.hasMoreTokens()) {
				String[] args = new String[stringTokenizer.countTokens()];
				for (int i = 0; i < args.length; i++) {
					args[i] = stringTokenizer.nextToken();
				}
				commandResult = commands.get(commandHeader).apply(args);
			} else {
				commandResult = commands.get(commandHeader).apply(null);
			}
		}

		return commandResult;
	}
	
	public static void clear() {
		commands.clear();
	}

}
