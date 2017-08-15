package com.jacklin.service;

import com.google.inject.Singleton;
import com.jacklin.enums.TransportType;
import com.jacklin.model.Transport;
import com.jacklin.repository.TransportDAO;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by iurii on 8/4/17.
 */
@Singleton
public class TransportService {
    private TransportDAO transportDAO;

    @Inject
    public TransportService(TransportDAO transportDAO) {
        this.transportDAO = transportDAO;
    }

    public Transport saveTransport(Transport transport) {
        Transport persisTransport = null;
        try {
            persisTransport = transportDAO.saveTransport(transport);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return persisTransport;
    }

    public Transport findTransportBySerialId(String serialID) {
        return transportDAO.findTransportBySerialID(serialID);
    }

    public List<Transport> findTransportByNumAndType(Integer transportNum, TransportType transportType) {
        return transportDAO.findTransportByNumberAndType(transportNum, transportType);
    }

    public List<Transport> findAllTransports() {
        return transportDAO.findAllTransport();
    }

    public void deleteTransportBySerId(String serialId) {
        transportDAO.deleteTransportBySerialId(serialId);
    }
}
