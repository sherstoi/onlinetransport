package com.jacklin.rest;

import com.jacklin.enums.TransportType;
import com.jacklin.model.Transport;
import com.jacklin.service.TransportService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by iurii on 8/2/17.
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransportRest {
    private TransportService transportService;

    @Inject
    public TransportRest(TransportService transportService) {
        this.transportService = transportService;
    }

    @POST
    @Path("/saveTransport")
    public Response saveTransport(Transport transport) {
        transportService.saveTransport(transport);
        return Response.status(Response.Status.CREATED).entity(transport).build();
    }

    @GET
    @Path("/findTransportBySerialID")
    public Response findTransportBySerialId (@QueryParam("serialId") String serialId) {
        Transport transport = transportService.findTransportBySerialId(serialId);
        return (transport != null) ?
                Response.status(Response.Status.FOUND).entity(transport).build() :
                Response.status(Response.Status.NOT_FOUND).entity(null).build();
    }

    @GET
    @Path("/findTransportByNumberAndType")
    public Response findTransportByNumberAndType (@QueryParam("transportNumber") Integer transportNum, @QueryParam("transportType") TransportType transportType) {
        List<Transport> transportList = transportService.findTransportByNumAndType(transportNum, transportType);
        return (transportList != null) ?
                Response.status(Response.Status.FOUND).entity(new GenericEntity<List<Transport>>(transportList){}).build() :
                Response.status(Response.Status.NOT_FOUND).entity(null).build();
    }

    @GET
    @Path("/findAllTransports")
    public Response findAllTransports () {
        List<Transport> transportList = transportService.findAllTransports();
        return Response.ok().entity(new GenericEntity<List<Transport>>(transportList){}).build();
    }
}
