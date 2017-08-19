package com.jacklin.repository;

import com.google.inject.Inject;
import com.jacklin.model.TransportCurrentLocationJrnl;
import org.bson.Document;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

import java.util.List;

/**
 * Created by iurii on 8/19/17.
 */
public class TransportCoordinateJrnlDAO extends BasicDAO<TransportCurrentLocationJrnl, String> {

    @Inject
    public TransportCoordinateJrnlDAO(Datastore datastore) {
        super(TransportCurrentLocationJrnl.class, datastore);
    }

    public List<TransportCurrentLocationJrnl> findAllTransportLocationsInJrnl() {
        return getDatastore().find(TransportCurrentLocationJrnl.class).asList();
    }

    public void bulkInsertOfTransportCoordinate(List<TransportCurrentLocationJrnl> transportCurrentLocationJrnls) {
        getDatastore().save(transportCurrentLocationJrnls);
    }

    public void deleteAllTransportJrnlCoord() {
        getDatastore().getMongo().getDatabase("jakdojade_db")
                .getCollection("transport_cur_location_jrnl")
                .deleteMany(new Document());
    }
}
