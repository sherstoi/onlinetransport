package com.jacklin.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.jacklin.model.TransportCurrentLocation;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by iurii on 8/16/17.
 */
@Singleton
public class TransportCoordinateService {
    private JedisPool jedisPool;

    @Inject
    public TransportCoordinateService(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
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
}
