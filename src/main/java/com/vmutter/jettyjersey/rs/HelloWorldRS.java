package com.vmutter.jettyjersey.rs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/rs")
public class HelloWorldRS {

	@GET
	@Path("/hello")
	@Produces(MediaType.APPLICATION_JSON)
	public Response hello() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString("Hello World");

		return Response.ok(json).build();
	}
}
