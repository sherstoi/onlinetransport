package com.jacklin.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jacklin.enums.TransportType;
import com.jacklin.model.Transport;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.dao.BasicDAO;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by iurii on 8/4/17.
 */
public class TransportDAO extends BasicDAO<Transport, String> {

    @Inject
    public TransportDAO(Datastore datastore) {super(Transport.class, datastore);}

    public List<Transport> findAllTransport() {
        return getDatastore().find(Transport.class).asList();
    }

    public Transport findTransportBySerialID(String transportSerialID) {
        return getDatastore().find(Transport.class).field("transportSerialID").equal(transportSerialID).get();
    }

    public List<Transport> findTransportByNumberAndType(Integer transportNumber, TransportType transportType) {
        return getDatastore().find(Transport.class)
                .field("transportNumber").equal(transportNumber)
                .field("transportType").equal(transportType)
                .asList();
    }

    public Transport saveTransport(Transport transport) throws JsonProcessingException {
        Key<Transport> transportKey = getDatastore().save(transport);
        return getDatastore().getByKey(Transport.class, transportKey);
    }
}
