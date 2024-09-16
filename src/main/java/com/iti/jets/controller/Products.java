package com.iti.jets.controller;

import com.iti.jets.exceptions.ResourceNotFoundException;
import com.iti.jets.model.Product;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.*;

/**
 *  /products [POST], /products [GET]
 *  /products/{id} [GET], /products/{id} [PUT], /products/{id} [DELETE]
 */

@Path("/products")
public class Products {

    private static Map<Long, Product> productsDB = new HashMap<>();
    private static Long idCounter = 1L;
    private static Long reqCounter = 1L;

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Product> getProducts() {
        System.out.println("reqCounter: " + reqCounter++);
        return new ArrayList<>(productsDB.values());
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getProductById(@PathParam("id") Long id, @Context UriInfo uriInfo) {

        try {
            Product product = productsDB.get(id);
            URI uri = uriInfo.getAbsolutePathBuilder().path(product.getId().toString()).build();
            System.out.println("reqCounter: " + reqCounter++);
            return Response.created(uri).entity(product).build();

        }catch (Exception e) {
            throw new ResourceNotFoundException("Product with ID:" + id + " Not Found");
        }
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response addProduct(Product product, @Context UriInfo uriInfo) {
        product.setId(idCounter++);
        productsDB.put(product.getId(), product);
        URI uri = uriInfo.getAbsolutePathBuilder().path(product.getId().toString()).build();
        System.out.println("reqCounter: " + reqCounter++);
        return Response.created(uri).entity(product).build();
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateProduct(@PathParam("id") Long id, Product updatedProduct, @Context UriInfo uriInfo) {

        try {
            Product product = productsDB.get(id);
            URI uri = uriInfo.getAbsolutePathBuilder().path(product.getId().toString()).build();
            product.setName(updatedProduct.getName());
            product.setPrice(updatedProduct.getPrice());
            System.out.println("reqCounter: " + reqCounter++);
            return Response.created(uri).entity(product).build();

        }catch (Exception e) {
            throw new ResourceNotFoundException("Update req: Product with ID:" + id + " Not Found");
        }

    }

    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteProduct(@PathParam("id") Long id, @Context UriInfo uriInfo) {

        try {
            Product product = productsDB.remove(id);
            URI uri = uriInfo.getAbsolutePathBuilder().path(product.getId().toString()).build();
            System.out.println("reqCounter: " + reqCounter++);
            return Response.created(uri).entity(product).build();

        }catch (Exception e) {
            throw new ResourceNotFoundException("Product with ID:" + id + " Not Found");

        }
    }

    @DELETE
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteProduct(@Context UriInfo uriInfo) {
        productsDB.clear();
        idCounter = 1L;

        System.out.println("reqCounter: " + reqCounter++);
        return Response.ok().build();
    }
}