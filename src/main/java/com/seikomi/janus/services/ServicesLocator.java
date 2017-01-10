package com.seikomi.janus.services;

import java.util.HashMap;
import java.util.Map;

import com.seikomi.janus.net.JanusServer;

public class ServicesLocator {
	private static Map<String, AbstractServices> services = new HashMap<>();

	protected static void load(AbstractServices service) {
		String serviceIdentifier = service.getClass().getSimpleName() + "@"
				+ Integer.toHexString(service.server.hashCode());
		System.out.println("Registered the service: " + serviceIdentifier);
		services.put(serviceIdentifier, service);
	}

	public static <T extends AbstractServices> T getService(Class<T> serviceClass, JanusServer associatedJanusServer) {
		return serviceClass.cast(getService(serviceClass.getSimpleName(), associatedJanusServer));
	}

	public static AbstractServices getService(String fullServiceName) {
		return services.get(fullServiceName);
	}

	public static AbstractServices getService(String serviceName, JanusServer associatedJanusServer) {
		return services.get(serviceName + "@" + Integer.toHexString(associatedJanusServer.hashCode()));
	}

}
