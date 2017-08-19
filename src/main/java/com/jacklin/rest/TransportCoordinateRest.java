package com.jacklin.rest;

import com.google.inject.Inject;
import com.jacklin.model.TransportCurrentLocation;
import com.jacklin.model.TransportCurrentLocationJrnl;
import com.jacklin.service.TransportCoordinateService;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by iurii on 8/16/17.
 */
@Path("/transportLocation")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransportCoordinateRest {

    private TransportCoordinateService transportCoordinateService;

    @Inject
    public TransportCoordinateRest(TransportCoordinateService transportCoordinateService) {
        this.transportCoordinateService = transportCoordinateService;
    }

    @POST
    @Path("/saveCoordinate")
    public Response saveTransport(TransportCurrentLocation transportCurrentLocation) {
        transportCoordinateService.saveCoordinate(transportCurrentLocation);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/getCurrentLocation")
    public Response getTransportLocation(@QueryParam("serialId") String serialId) {
        TransportCurrentLocation transportCurrentLocation = transportCoordinateService.getTransportLocation(serialId);
        return Response.ok().entity(transportCurrentLocation).build();
    }

    @GET
    @Path("/getAllJrnlCoord")
    public Response getAllTransportCoordinateFromJrnl() {
        List<TransportCurrentLocationJrnl> transportCurrentLocationJrnlList = transportCoordinateService.findAllCoordinateFromJrnl();
        return Response.ok().entity(new GenericEntity<List<TransportCurrentLocationJrnl>>
                (transportCurrentLocationJrnlList){}).build();
    }

    @DELETE
    @Path("/cleanTransportCoord")
    public Response cleanTransportCoordJrnl() {
        transportCoordinateService.cleanTransportCoordFromJrnl();
        return Response.ok().build();
    }
}
