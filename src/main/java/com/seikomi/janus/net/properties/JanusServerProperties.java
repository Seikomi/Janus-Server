package com.seikomi.janus.net.properties;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Path;
import java.util.Properties;

import com.seikomi.janus.utils.Utils;

/**
 * This class is the main set of properties use to initialize the Janus server.
 * It is link to a {@code Properties} class, provide automatic properties file
 * creation on file system and check if the properties set in the file are the
 * one expected by the server and give direct access on property with a type
 * conversion.
 * 
 * @author Nicolas SYMPHORIEN (nicolas.symphorien@gmail.com)
 *
 */
public class JanusServerProperties {

	/** The {@code Properties} instance use to store server properties. */
	private Properties properties;

	/**
	 * Create a new instance of Janus server properties file with the properties
	 * set in the file at the {@code propertiesFilePath}. If it doesn't exist,
	 * create a new file and set the properties to her default values described
	 * in {@link JanusServerDefaultProperties}.
	 * 
	 * @param propertiesFilePath
	 *            the properties file path
	 * @throws IOException
	 *             if an error occurs at the creation or at the reading of the
	 *             read the property file properties
	 */
	public JanusServerProperties(Path propertiesFilePath) throws IOException {
		properties = new Properties();

		File propertiesFile = propertiesFilePath.toFile();
		if (!propertiesFile.exists()) {
			createDefaultPropertiesFile(propertiesFile);
		}

		readProperties(propertiesFile);

	}

	/**
	 * Create the properties file on file system with default values.
	 * 
	 * @param propertiesFile
	 *            the properties file to create
	 * @throws IOException
	 *             if an error occurs at the creation the property file
	 *             properties
	 */
	private void createDefaultPropertiesFile(File propertiesFile) throws IOException {
		Writer writer = new FileWriter(propertiesFile);

		Properties defaultProperties = new Properties();
		for (JanusServerDefaultProperties defaultProperty : JanusServerDefaultProperties.values()) {
			defaultProperties.setProperty(defaultProperty.getPropertyName(), defaultProperty.getPropertyValue());
		}
		defaultProperties.store(writer, "Server properties file");
	}

	/**
	 * Read the properties in the properties file.
	 * 
	 * @param propertiesFile
	 *            the properties file
	 * @throws IOException
	 *             if an error occurs at the reading of the read the property
	 *             file properties
	 */
	private void readProperties(File propertiesFile) throws IOException {
		Reader reader = new FileReader(propertiesFile);
		properties.load(reader);

		if (!isValid()) {
			throw new IOException("one or many properties are malformed");
		}

	}
	
	/**
	 * Check if the properties file is well formed.
	 * @return {@code true} if all properties is well formed, {@code false} otherwise.
	 */
	private boolean isValid() {
		boolean valid = true;

		for (JanusServerDefaultProperties defaultProperty : JanusServerDefaultProperties.values()) {
			String propertyName = defaultProperty.getPropertyName();

			valid &= properties.containsKey(propertyName);
			valid &= Utils.isInteger(properties.getProperty(propertyName));
		}

		return valid;
	}
	
	/**
	 * Get the data port set in the properties file.
	 * @return the data port
	 */
	public int getDataPort() {
		return Integer.parseInt(properties.getProperty(JanusServerDefaultProperties.DATA_PORT.getPropertyName()));

	}
	
	/**
	 * Get the command port set in the properties file.
	 * @return the command port
	 */
	public int getCommandPort() {
		return Integer.parseInt(properties.getProperty(JanusServerDefaultProperties.COMMAND_PORT.getPropertyName()));
	}

}
