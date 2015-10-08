package com.vmutter.jettyjersey.rs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class HelloWorldRS {

	@GET
	@Path("/hello")
	@Produces(MediaType.APPLICATION_JSON)
	public Response helloWorld() {
		return Response.ok("Hello World").build();
	}
}
