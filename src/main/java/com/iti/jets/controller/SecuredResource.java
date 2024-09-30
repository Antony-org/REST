package com.iti.jets.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

//@Path("/secured/resource")
@Path("/resource")
public class SecuredResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getSecuredMessage() {
        return Response.ok("This is a secured message!").build();
    }
}