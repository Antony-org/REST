package com.iti.jets.providers;


import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.StringTokenizer;

@Provider
public class SecurityFilter implements ContainerRequestFilter {
    private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
    private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        List<String> authHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER_KEY);
        if (authHeader != null && !authHeader.isEmpty()) {
            String authToken = authHeader.get(0);
            authToken = authToken.replace(AUTHORIZATION_HEADER_PREFIX, "");
            // Decode the Base64 string to byte[]
            byte[] decodedBytes = Base64.getDecoder().decode(authToken);
            // Convert the byte[] to a human-readable string using UTF-8 encoding
            String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);
            System.out.println(decodedString);
            System.out.println("===========================");
            StringTokenizer tokenizer = new StringTokenizer(decodedString, ":");
            String username = tokenizer.nextToken();
            String password = tokenizer.nextToken();
            if ("tony".equals(username) && "tony".equals(password)) {
                return;
            } else {
                Response unauthorizedStatus = Response.status(Response.Status.FORBIDDEN).entity("username or password is incorrect.").build();
                requestContext.abortWith(unauthorizedStatus);
            }
        }
        Response unauthorizedStatus = Response.status(Response.Status.UNAUTHORIZED).entity("user cannot access the resource.").build();
        requestContext.abortWith(unauthorizedStatus);
    }
}