package com.seikomi.janus.commands;

import com.seikomi.janus.net.JanusServer;
import com.seikomi.janus.services.Locator;
import com.seikomi.janus.services.UploadService;

/**
 * Upload command. Download on the client the files passed in arguments. Return
 * {@code #DOWNLOAD STARTED} when the upload begin.
 * 
 * @author Nicolas SYMPHORIEN (nicolas.symphorien@gmail.com)
 *
 */
public class Upload extends JanusCommand {

	public Upload(JanusServer server) {
		super(server);
	}

	@Override
	public String[] apply(String[] args) {
		UploadService downloadService = Locator.getService(UploadService.class, server);
		downloadService.receive(args);

		return new String[] { "#UPLOAD STARTED" };
	}

}
