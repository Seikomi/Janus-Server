package com.seikomi.janus.net.tasks;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seikomi.janus.net.JanusServer;

/**
 * Janus task to handle files transmission between a server and a client. This
 * task open the data port, sends and receives files and close the data port
 * when the task is finish.
 * 
 * @author Nicolas SYMPHORIEN (nicolas.symphorien@gmail.com)
 *
 */
public class DataTransferTask extends JanusTask {
	static final Logger LOGGER = LoggerFactory.getLogger(ConnectTask.class);

	private static final int BUFFER_SIZE = 1024; // 1 Kio Buffer

	private ServerSocket dataServerSocket;

	private BufferedOutputStream out;
	private BufferedInputStream in;

	private String[] fileNames;
	private int fileIndex;

	private boolean isDownloadTransfert;

	/**
	 * Construct a data transfert task associated with the Janus server to send
	 * the files referenced by their file names if {@code isDownloadTransfert}
	 * is {@code true}, receive the files otherwise.
	 * 
	 * @param server
	 *            the Janus server
	 * @param fileNames
	 *            an array of file names of files to transmit
	 * @param isDownloadTransfert
	 *            flag to indicate the direction of the data transfer :
	 *            {@code true} for sending and {@code false} to receiving.
	 */
	public DataTransferTask(JanusServer server, String[] fileNames, boolean isDownloadTransfert) {
		super(server);
		this.fileNames = fileNames;
		this.isDownloadTransfert = isDownloadTransfert;
	}

	/**
	 * Open the data port and wait for connection.
	 */
	@Override
	protected void beforeLoop() {
		try {
			dataServerSocket = new ServerSocket(((JanusServer) server).getDataPort());
			Socket dataSocket = dataServerSocket.accept();
			out = new BufferedOutputStream(dataSocket.getOutputStream(), BUFFER_SIZE);
			in = new BufferedInputStream(dataSocket.getInputStream(), BUFFER_SIZE);
		} catch (IOException e) {
			LOGGER.error("An error occurs during initialization of data transmission", e);
		}
		fileIndex = 0;
	}

	/**
	 * Sends each files if they exists on file system or receive files from a
	 * client.
	 */
	@Override
	protected void loop() {
		if (fileIndex < fileNames.length) {
			String fileName = fileNames[fileIndex];
			if (isDownloadTransfert) {
				File fileToSend = new File(fileName);
				if (fileToSend.exists()) {
					sendFile(fileName);
				} else {
					LOGGER.debug(fileName + " not found");
				}
			} else {
				receiveFile(fileName);
			}
			fileIndex++;
		} else {
			endLoop();
		}
	}

	/**
	 * Close the data port when all files has been send or receive.
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
	 * Sends the file locate to {@code path} on file system on data port channel
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
	 * Receives the file from a client and write it on the file system in the
	 * directory referenced by the {@code serverFileRootDirectory} property. The
	 * data stream from the client must be on the following format :
	 * {@code [filelength on 8 bytes (LONG)] + [data]}.
	 * 
	 * @param path
	 *            the file path
	 */
	private void receiveFile(String path) {
		String directory = ((JanusServer) server).getProperties().getProperty("serverFileRootDirectory");
		File file = new File(directory + path);

		try (final BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file), BUFFER_SIZE)) {
			// Header (file length)
			byte[] header = new byte[Long.BYTES];
			in.read(header, 0, header.length);
			long fileLenth = bytesToLong(header);

			// Data
			byte[] bytes = new byte[BUFFER_SIZE];

			long numberOfByteRead = 0;
			do {
				if (numberOfByteRead + BUFFER_SIZE <= fileLenth) {
					in.read(bytes, 0, BUFFER_SIZE);
					numberOfByteRead += BUFFER_SIZE;
				} else {
					int numberOfBytesToReadRemaining = Math.toIntExact(fileLenth - numberOfByteRead);
					in.read(bytes, 0, numberOfBytesToReadRemaining);
					numberOfByteRead += numberOfBytesToReadRemaining;
				}
				out.write(bytes);
				out.flush();
			} while (numberOfByteRead < fileLenth);
		} catch (IOException e) {
			LOGGER.error("An error occurs during the reception of data", e);
		}

	}

	/**
	 * Converts a {@code long} variable in the corresponding array of bytes.
	 * 
	 * @param i
	 *            the {@code long} variable to convert.
	 * @return the corresponding array of bytes (8 bytes)
	 */
	private long bytesToLong(byte[] b) {
		ByteBuffer buffer = ByteBuffer.wrap(b);
		return buffer.getLong();
	}

	public byte[] longToBytes(long i) {
		ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
		buffer.putLong(i);
		return buffer.array();
	}

	public void addFiles(String[] fileNames, boolean isDownloadTransfert) {
		// TODO Adding files during upload process

	}
}
