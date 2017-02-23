package com.seikomi.janus.services;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seikomi.janus.net.JanusServer;

public class ServicesLocator {
	static final Logger LOGGER = LoggerFactory.getLogger(ServicesLocator.class);
	
	private static Map<String, JanusService> services = new HashMap<>();
	
	private ServicesLocator() {
		// Hide public constructor.
	}

	protected static void load(JanusService service) {
		String serviceIdentifier = service.getClass().getSimpleName() + "@"
				+ Integer.toHexString(service.server.hashCode());
		LOGGER.debug("Registered the service: " + serviceIdentifier);
		services.put(serviceIdentifier, service);
	}

	public static <T extends JanusService> T getService(Class<T> serviceClass, JanusServer associatedJanusServer) {
		return serviceClass.cast(getService(serviceClass.getSimpleName(), associatedJanusServer));
	}

	public static JanusService getService(String fullServiceName) {
		return services.get(fullServiceName);
	}

	public static JanusService getService(String serviceName, JanusServer associatedJanusServer) {
		return services.get(serviceName + "@" + Integer.toHexString(associatedJanusServer.hashCode()));
	}

}
