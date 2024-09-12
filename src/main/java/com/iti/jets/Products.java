package com.iti.jets;

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

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Product> getProducts() {
        return new ArrayList<>(productsDB.values());
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getProductById(@PathParam("id") Long id, @Context UriInfo uriInfo) {
        Product product = productsDB.get(id);
        URI uri = uriInfo.getAbsolutePathBuilder().path(product.getId().toString()).build();

        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Product not found").build();
        }

        return Response.created(uri).entity(product).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response addProduct(Product product, @Context UriInfo uriInfo) {
        product.setId(idCounter++);
        productsDB.put(product.getId(), product);

        URI uri = uriInfo.getAbsolutePathBuilder().path(product.getId().toString()).build();
        return Response.created(uri).entity(product).build();
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateProduct(@PathParam("id") Long id, Product updatedProduct, @Context UriInfo uriInfo) {
        Product existingProduct = productsDB.get(id);
        URI uri = uriInfo.getAbsolutePathBuilder().path(updatedProduct.getId().toString()).build();

        if (existingProduct == null) {
            return Response.created(uri).entity("Product not found").build();
        }


        existingProduct.setName(updatedProduct.getName());
        existingProduct.setPrice(updatedProduct.getPrice());
        return Response.created(uri).entity(existingProduct).build();
    }

    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteProduct(@PathParam("id") Long id, @Context UriInfo uriInfo) {
        Product product = productsDB.remove(id);
        URI uri = uriInfo.getAbsolutePathBuilder().path(product.getId().toString()).build();

        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Product not found").build();
        }
        return Response.created(uri).entity(product).build();
    }
}