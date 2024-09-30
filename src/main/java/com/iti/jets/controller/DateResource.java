package com.iti.jets.controller;

import com.iti.jets.model.MyDate;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("date/{dateString}")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
public class DateResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getDate(@PathParam("dateString") MyDate myDate) {
        return myDate.toString();
    }
}
