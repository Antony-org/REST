package com.iti.jets.providers;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyReader;
import jakarta.ws.rs.ext.Provider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Provider
@Consumes(MediaType.TEXT_PLAIN)
public class DateMessageBodyReader implements MessageBodyReader<Date> {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return Date.class.isAssignableFrom(type);
    }

    @Override
    public Date readFrom(Class<Date> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {

        // Read the input stream into a string
        BufferedReader reader = new BufferedReader(new InputStreamReader(entityStream));
        String dateStr = reader.readLine();

        // Ensure the input is trimmed
        dateStr = dateStr.trim();

        // Parse the string to a Date object
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            throw new WebApplicationException("Invalid date format. Expected format: " + DATE_FORMAT, e);
        }
    }
}