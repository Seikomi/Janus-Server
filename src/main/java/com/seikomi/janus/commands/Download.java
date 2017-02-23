package com.seikomi.janus.commands;

import com.seikomi.janus.net.JanusServer;
import com.seikomi.janus.services.DataTranferService;
import com.seikomi.janus.services.Locator;

/**
 * Download command.Upload on the client the files passed in arguments. Return
 * {@code #DOWNLOAD STARTED} when the upload begin.
 * 
 * @author Nicolas SYMPHORIEN (nicolas.symphorien@gmail.com)
 *
 */
public class Download extends JanusCommand {

	public Download(JanusServer server) {
		super(server);
	}

	@Override
	public String[] apply(String[] args) {
		String[] responce;
		
		DataTranferService downloadService = Locator.getService(DataTranferService.class, server);
		
		if (args == null || args.length == 0) {
			responce = new String[] { "#DOWNLOAD NO FILES TO SEND" };
		} else {
			downloadService.send(args);
			responce = new String[] { "#DOWNLOAD STARTED" };
		}

		return responce;
	}

}
