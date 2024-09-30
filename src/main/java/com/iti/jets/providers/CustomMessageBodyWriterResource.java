package com.iti.jets.providers;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.Calendar;
import java.util.Date;

@Path("customWriter")
public class CustomMessageBodyWriterResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Date getDate() {
        return Calendar.getInstance().getTime();
    }
}