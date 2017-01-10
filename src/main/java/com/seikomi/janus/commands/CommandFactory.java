package com.seikomi.janus.commands;

import java.util.HashMap;
import java.util.StringTokenizer;

import com.seikomi.janus.net.JanusServer;

/**
 * This class is the factory of commands use by Janus server. She stores a set
 * of commands and execute the command ask by a client. This factory <b>need<b/>
 * to be initialize before use it by the call of {@code init()} method.
 * 
 * @author Nicolas SYMPHORIEN (nicolas.symphorien@gmail.com)
 *
 */
public class CommandFactory {
	private final HashMap<String, AbstractCommand> commands;

	/**
	 * Private constructor call at the initialization of the factory.
	 */
	private CommandFactory(JanusServer server) {
		commands = new HashMap<>();
	}

	/**
	 * Initialize the command factory with all commands set in her
	 * {@code init()} method.
	 * @param server TODO
	 * 
	 * @return the command factory with commands charged
	 */
	public static CommandFactory init(JanusServer server) {
		CommandFactory cf = new CommandFactory(server);

		cf.addCommand("#EXIT", new Exit(server));
		cf.addCommand("#DOWNLOAD", new Download(server));

		return cf;
	}

	/**
	 * Add an external command in the command factory.
	 * 
	 * @param name
	 *            the associated name of the command
	 * @param command
	 *            the command
	 */
	public void addCommand(String name, AbstractCommand command) {
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
	public String[] executeCommand(String commandString) {
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

}
