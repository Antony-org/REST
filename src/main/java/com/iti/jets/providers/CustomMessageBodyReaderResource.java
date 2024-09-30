package com.iti.jets.providers;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Date;

@Path("customReader")
public class CustomMessageBodyReaderResource {

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public Response postDate(Date date) {
        if (date == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Date is invalid or could not be parsed.").build();
        }
        return Response.ok("Received date: " + date).build();
    }
}
