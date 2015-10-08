package com.vmutter.jettyjersey.rs;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/hello")
public class HelloWorldRS {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response hello() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString("Hello World.");

		return Response.ok(json).build();
	}

	@GET
	@Path("/basic")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed("basic")
	public Response security(@Context SecurityContext securityContext) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString("You are allowed to view this content.");

		return Response.ok(json).build();
	}
}
