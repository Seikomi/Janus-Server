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

/**
 * Janus task to handle files transmission from the server to a client. This
 * task open the data port, trasmit files and clase the data when the task is
 * finish.
 * 
 * @author Nicolas SYMPHORIEN (nicolas.symphorien@gmail.com)
 *
 */
public class DownloadTask extends JanusTask {
	static final Logger LOGGER = LoggerFactory.getLogger(ConnectTask.class);

	private static final int BUFFER_SIZE = 1024; // 1 Kio Buffer

	private BufferedOutputStream out;

	private String[] fileNames;
	private int fileIndex;

	private ServerSocket dataServerSocket;

	/**
	 * Construct a download task associated with the Janus server to
	 * transmitting the files referenced by their file names.
	 * 
	 * @param server
	 *            the Janus server
	 * @param fileNames
	 *            an array of file names of files to transmit
	 */
	public DownloadTask(JanusServer server, String[] fileNames) {
		super(server);
		this.fileNames = fileNames;
	}

	/**
	 * Open the data port and wait for connection.
	 */
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

	/**
	 * Sends each files if they exists on file system.
	 */
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

	/**
	 * Close the data port when all files has been send.
	 */
	@Override
	protected void afterLoop() {
		try {
			out.close();
			dataServerSocket.close();
		} catch (IOException e) {
			LOGGER.error("An error occurs during the closing of data transmission pipe", e);
		}
	}

	/**
	 * Send the file locate to {@code path} on file system on data port channel
	 * in binary with the following format :
	 * {@code [filelength on 8 bytes (LONG)] + [data]}.
	 * 
	 * @param path
	 *            the file path
	 */
	private void sendFile(String path) {
		File file = new File(path);
		try (final BufferedInputStream in = new BufferedInputStream(new FileInputStream(file), BUFFER_SIZE)) {
			// Header (file length)
			byte[] header = longToBytes(file.length());
			out.write(header);
			
			// Data
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
	
	/**
	 * Converts a {@code long} variable in the corresponding array of bytes.
	 * @param i the {@code long} variable to convert.
	 * @return the corresponding array of bytes (8 bytes)
	 */
	public byte[] longToBytes(long i) {
		ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
		buffer.putLong(i);
		return buffer.array();
	}

}
