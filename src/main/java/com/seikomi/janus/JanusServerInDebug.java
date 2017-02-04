package com.seikomi.janus;

import com.seikomi.janus.net.JanusServer;
import com.seikomi.janus.net.properties.JanusServerProperties;

/**
 * Instantiable class of Janus server for testing purpose.
 * 
 * @author Nicolas SYMPHORIEN (nicolas.symphorien@gmail.com)
 */
public class JanusServerInDebug extends JanusServer {
	
	/**
	 * Create a new instance of Janus server for testing and configure it with the
	 * {@code .properties} file pass in argument.
	 * 
	 * @param serverProperties
	 *            the server {@code .properties} file
	 */
	public JanusServerInDebug(JanusServerProperties serverProperties) {
		super(serverProperties);
	}

	@Override
	protected void loadContext() {
		// nothing to do
	}

}
