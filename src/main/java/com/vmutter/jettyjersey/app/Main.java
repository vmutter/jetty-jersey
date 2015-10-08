package com.vmutter.jettyjersey.app;

import java.util.Collections;

import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.security.Constraint;
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

		LoginService loginService = new HashLoginService("MyRealm", "src/main/resources/realm.properties");
		ConstraintSecurityHandler security = new ConstraintSecurityHandler();

		Constraint constraint = new Constraint();
        constraint.setName("auth");
        constraint.setAuthenticate(true);
        constraint.setRoles(new String[] { "user", "admin" });

        ConstraintMapping mapping = new ConstraintMapping();
        mapping.setPathSpec("/*");
        mapping.setConstraint(constraint);

        security.setConstraintMappings(Collections.singletonList(mapping));
        security.setAuthenticator(new BasicAuthenticator());
        security.setLoginService(loginService);

		Server jettyServer = new Server(8080);
		ServletContextHandler context = new ServletContextHandler(jettyServer, "/");

		context.addServlet(jerseyServlet, "/rs/*");
		context.setHandler(security);
		context.addBean(loginService);

		try {
			jettyServer.start();
			jettyServer.join();
		} finally {
			jettyServer.destroy();
		}
	}
}
