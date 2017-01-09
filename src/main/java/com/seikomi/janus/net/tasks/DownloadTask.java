package com.seikomi.janus.net.tasks;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seikomi.janus.net.JanusServer;

public class DownloadTask extends JanusTask {
	static final Logger LOGGER = LoggerFactory.getLogger(ConnectTask.class);

	private static final int BUFFER_SIZE = 1024; // 1 Kio Buffer

	private BufferedOutputStream out;

	private String[] fileNames;
	private int fileIndex;

	private ServerSocket dataServerSocket;

	public DownloadTask(JanusServer server, String[] fileNames) {
		super(server);
		this.fileNames = fileNames;
	}

	@Override
	protected void beforeLoop() {
		try {
			dataServerSocket = new ServerSocket(server.getDataPort());
			Socket dataSocket = dataServerSocket.accept();
			out = new BufferedOutputStream(dataSocket.getOutputStream(), BUFFER_SIZE);
		} catch (IOException e) {
			LOGGER.error("An error occurs during initialization of data transmission", e);
		}
		fileIndex = 0;
	}

	@Override
	protected void loop() {
		if (fileIndex < fileNames.length) {
			String fileName = fileNames[fileIndex];
			File fileToSend = new File(fileName);
			if (fileToSend.exists()) {
				sendFile(fileName);
			} else {
				LOGGER.debug(fileName + " not found");
			}
			fileIndex++;
		} else {
			endLoop();
		}
	}

	@Override
	protected void afterLoop() {
		try {
			out.close();
			dataServerSocket.close();
		} catch (IOException e) {
			LOGGER.error("An error occurs during the closing of data transmission pipe", e);
		}
	}

	private void sendFile(String path) {
		File file = new File(path);
		try (final BufferedInputStream in = new BufferedInputStream(new FileInputStream(file), BUFFER_SIZE)) {

			byte[] header = longToBytes(file.length());
			out.write(header);

			byte[] bytes = new byte[BUFFER_SIZE];
			int numberOfByteRead;
			do {
				numberOfByteRead = in.read(bytes);
				if (numberOfByteRead != -1) {
					out.write(bytes, 0, numberOfByteRead);
				}
			} while (numberOfByteRead != -1);

			out.flush();
		} catch (IOException e) {
			LOGGER.error("An error occurs during the sending of data", e);
		}
	}

	public byte[] longToBytes(long i) {
		ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
		buffer.putLong(i);
		return buffer.array();
	}

	public void addFiles(String[] fileNames) {
		// TODO Adding files during upload process
		
	}

}
