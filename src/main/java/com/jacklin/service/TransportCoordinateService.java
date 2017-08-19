package com.jacklin.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.jacklin.converter.TransportCoordinateConverter;
import com.jacklin.model.TransportCurrentLocation;
import com.jacklin.model.TransportCurrentLocationJrnl;
import com.jacklin.repository.TransportCoordinateJrnlDAO;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * Created by iurii on 8/16/17.
 */
@Singleton
public class TransportCoordinateService {
    private JedisPool jedisPool;
    private TransportCoordinateJrnlDAO transportCoordinateJrnlDAO;

    @Inject
    public TransportCoordinateService(JedisPool jedisPool, TransportCoordinateJrnlDAO transportCoordinateJrnlDAO) {
        this.jedisPool = jedisPool;
        this.transportCoordinateJrnlDAO = transportCoordinateJrnlDAO;
    }

    public void saveCoordinate(TransportCurrentLocation transportCurrentLocation) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set(transportCurrentLocation.getTransportSerialID(),
                    TransportCoordinateConverter.convertTransportCoordinateToString(transportCurrentLocation));
        }
    }

    public TransportCurrentLocation getTransportLocation(String serialId) {
        TransportCurrentLocation transportCurrentLocation = null;
        try (Jedis jedis = jedisPool.getResource()) {
            String coordinate = jedis.get(serialId);
            if (!StringUtils.isBlank(coordinate)) {
                transportCurrentLocation = TransportCoordinateConverter.convertTransportCoordinateToObject(serialId, coordinate);
            }
        }
        return transportCurrentLocation;
    }

    public void saveCoordinateToJrnl(List<TransportCurrentLocationJrnl> transportCurrentLocationJrnlList) {
        transportCoordinateJrnlDAO.bulkInsertOfTransportCoordinate(transportCurrentLocationJrnlList);
    }

    public List<TransportCurrentLocationJrnl> findAllCoordinateFromJrnl() {
        return transportCoordinateJrnlDAO.findAllTransportLocationsInJrnl();
    }

    public void cleanTransportCoordFromJrnl() {
        transportCoordinateJrnlDAO.deleteAllTransportJrnlCoord();
    }
}
