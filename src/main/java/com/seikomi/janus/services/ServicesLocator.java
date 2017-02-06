package com.seikomi.janus.services;

import java.util.HashMap;
import java.util.Map;

import com.seikomi.janus.net.JanusServer;

public class ServicesLocator {
	private static Map<String, JanusService> services = new HashMap<>();
	
	private ServicesLocator() {
		// Hide public constructor.
	}

	protected static void load(JanusService service) {
		String serviceIdentifier = service.getClass().getSimpleName() + "@"
				+ Integer.toHexString(service.server.hashCode());
		System.out.println("Registered the service: " + serviceIdentifier);
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
