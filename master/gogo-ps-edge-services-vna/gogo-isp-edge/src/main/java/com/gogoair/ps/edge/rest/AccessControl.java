package com.gogoair.ps.edge.rest;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.gogoair.ps.edge.domain.NewUserAccount;

/**
 * Create Account.
 * 
 * @author rboxall
 *
 */
@Singleton
@Path("v1/acg/")
public class AccessControl {

	@POST
	@Path("service")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response activateService(NewUserAccount account) {
		return Response.status(Status.CREATED).type(MediaType.APPLICATION_JSON).entity("created").build();
	}
	
	
	@DELETE
	@Path("service")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deactivateService(NewUserAccount account) {
		return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity("created").build();
	}

}
