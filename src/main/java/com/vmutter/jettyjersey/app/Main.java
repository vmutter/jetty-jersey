package com.vmutter.jettyjersey.app;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.glassfish.jersey.servlet.ServletContainer;

public class Main {

	public static void main(String[] args) throws Exception {
		ResourceConfig config = new ResourceConfig();
		config.register(RolesAllowedDynamicFeature.class);
        config.packages("com.vmutter.jettyjersey.rs");

        ServletHolder jerseyServlet = new ServletHolder(new ServletContainer(config));
        jerseyServlet.setInitOrder(0);

        Server jettyServer = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(jettyServer, "/");

        context.addServlet(jerseyServlet, "/rs/*");

		try {
			jettyServer.start();
			jettyServer.join();
		} finally {
			jettyServer.destroy();
		}
	}
}
