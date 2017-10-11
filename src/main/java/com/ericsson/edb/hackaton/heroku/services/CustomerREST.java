package com.ericsson.edb.hackaton.heroku.services;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ericsson.edb.hackaton.heroku.mediator.CustomerServicesMediator;
import com.ericsson.edb.hackaton.heroku.model.Customer;
import com.ericsson.edb.hackaton.heroku.services.support.ErrorMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path("/v1")
@Stateless
public class CustomerREST {

    @Inject
    CustomerServicesMediator customerServices;

    @PostConstruct
    public void initCustomerService() {
        System.out.println("----------");
        System.out.println("---------- Customer interface Initialized successfully ----------");
        System.out.println("----------");
    }

    @PreDestroy
    public void finishCustomerService() {
        System.out.println("----------");
        System.out.println("---------- Customer interface Finished successfully ----------");
        System.out.println("----------");
    }

    /**
     * Lists the existing customers.
     * A customer is defined by id, name and email
     * id: integer
     * name: an ordinary string
     * email: an ordinary string
     *
     * e.g.
     * list all customers:
     *    http://localhost:8080/hackatonserver/api/core/v1/customers
     * get customer per id:
     *    http://localhost:8080/hackatonserver/api/core/v1/customers/<id>
     */
    @GET
    @Path("/customers{p:/?}{id:(.*)}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getSchedules(@PathParam(value = "id") int id) {
        Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();

        List<Customer> customers = null;
        //as the id pathparam will be handled by rest api, we will always have this here!
        try {
          customers = customerServices.getCustomers(id);
        } catch (Exception e) {
          e.printStackTrace();
          ErrorMessage error = new ErrorMessage(Response.Status.BAD_REQUEST, new Exception("Failed to get Customer", e).getMessage());
          return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(error)).build(); // 400 - BadRequest
      }
        
        String jsonInString = gson.toJson(customers);

        return Response.status(Response.Status.OK).entity(jsonInString).build(); // 200 - OK
    }

    /**
     * Creates a Customer.
     * The customer is defined by id, name, email
     * id: an ordinary number
     * name: an ordinary string
     * email: an ordinary string
     *
     * This interface expects a JSON payload with the customer information
     *
     * e.g.
     * Creates a customer:
     *    http://localhost:8080/hackatonserver/api/controller/v1/customers
     *
     *    {
     *       "id" : "1000",
     *       "name" : "john snow"
     *       "email" : "john.snow@hackaton.com"
     *    }
     *
     */
    @POST
    @Path("/customers")
    @Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + "; charset=utf-8")
    public Response createCustomers(Customer customer) {
        Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
        try {
            if (!customer.getName().trim().isEmpty()) {
            } else {
                ErrorMessage error = new ErrorMessage(Response.Status.BAD_REQUEST, "Customer name is empty!");
                return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(error)).build(); // 400 - BadRequest
            }
            customerServices.createCustomers(customer);
            String jsonInString = gson.toJson(customer);

            return Response.status(Response.Status.CREATED).entity(jsonInString).build(); // 201 - Created
        } catch (Exception e) {
            e.printStackTrace();
            ErrorMessage error = new ErrorMessage(Response.Status.BAD_REQUEST, new Exception("Failed to create Customer id["+customer.getId()+"]!", e).getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(error)).build(); // 400 - BadRequest
        }
    }

    /**
     * Deletes a customer.
     * The customer is defined by an id
     * name: an ordinary string
     * 
     * e.g.
     * Deletes a customer:
     *  
     * http://localhost:8080/hackatonserver/api/controller/v1/customers/<customer_id>
     */
    @DELETE
    @Path("/customers{p:/?}{id:(.*)}")
    @Consumes(MediaType.APPLICATION_JSON + "; charset=utf-8")
    public Response deleteCustomers(@PathParam(value = "id") String id) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        int numberOfCustomers = 0;
        try {
          numberOfCustomers = customerServices.deleteCustomers(Integer.parseInt(id));
          
          if (numberOfCustomers == 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
          ErrorMessage error = new ErrorMessage(Response.Status.NOT_FOUND, "Customer id["+id+"] nof found!");
          return Response.status(Response.Status.NOT_FOUND).entity(gson.toJson(error)).build(); // 404 - Not Found
        } catch (SQLException e) {
          e.printStackTrace();
          ErrorMessage error = new ErrorMessage(Response.Status.NOT_FOUND, new Exception("Failed to delete Customer id["+id+"]!", e).getMessage());
          return Response.status(Response.Status.NOT_FOUND).entity(gson.toJson(error)).build(); // 404 - Not Found
        }

        return Response.status(Response.Status.NO_CONTENT).build(); // 204 - No Content
    }

}
