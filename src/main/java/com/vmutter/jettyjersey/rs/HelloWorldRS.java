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

	@Context
	private SecurityContext securityContext;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response hello() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString("Hello World.");

		return Response.ok(json).build();
	}

	@GET
	@Path("/user")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "user" })
	public Response user() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper
				.writeValueAsString(securityContext.getUserPrincipal().getName() + " is allowed to view this content.");

		return Response.ok(json).build();
	}

	@GET
	@Path("/admin")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "admin" })
	public Response admin() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper
				.writeValueAsString(securityContext.getUserPrincipal().getName() + " is allowed to view this content.");

		return Response.ok(json).build();
	}

	@GET
	@Path("/principal")
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response principal() throws JsonProcessingException {
		if (securityContext == null || securityContext.getUserPrincipal() == null) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(securityContext.getUserPrincipal().getName());

		return Response.ok(json).build();
	}
}
