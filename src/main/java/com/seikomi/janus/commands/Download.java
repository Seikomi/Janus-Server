package com.seikomi.janus.commands;

import com.seikomi.janus.net.JanusServer;
import com.seikomi.janus.services.DownloadService;
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
		DownloadService downloadService = Locator.getService(DownloadService.class, server);
		downloadService.send(args);

		return new String[] { "#DOWNLOAD STARTED" };
	}

}
