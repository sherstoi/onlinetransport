package com.jacklin.job;

import com.google.inject.Inject;
import com.jacklin.converter.TransportCoordinateConverter;
import com.jacklin.model.TransportCurrentLocationJrnl;
import com.jacklin.repository.TransportCoordinateJrnlDAO;
import org.apache.commons.collections4.CollectionUtils;
import org.mongodb.morphia.Datastore;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iurii on 8/17/17.
 */
public class CacheUploaderJob implements Runnable {
    private static final String ALL_KEYS = "*";
    private static final String ZERO = "0";

    private JedisPool jedisPool;
    private Datastore datastore;

    @Inject
    public CacheUploaderJob(JedisPool jedisPool, Datastore datastore) {
        this.jedisPool = jedisPool;
        this.datastore = datastore;
    }

    @Override
    public void run() {
        System.out.println("Job is running!");
        try (Jedis jedis = jedisPool.getResource()) {
            ScanParams scanParams = buildScanParams();
            String cursor = ScanParams.SCAN_POINTER_START;
            boolean isCompleted = false;
            while (!isCompleted) {
                ScanResult<String> scanResult = jedis.scan(cursor, scanParams);

                List<String> result = scanResult.getResult();
                if (CollectionUtils.isNotEmpty(result)) {
                    datastore.save(convertStringToTransportCoordJrnl(result, jedis));
                }

                if (ZERO.equals(scanResult.getStringCursor())) {
                    isCompleted = true;
                }
            }
        }
        System.out.println("Job has been finished!");
    }

    private ScanParams buildScanParams() {
        ScanParams scanParams = new ScanParams();
        scanParams.match(ALL_KEYS);
        return scanParams;
    }

    private List<TransportCurrentLocationJrnl> convertStringToTransportCoordJrnl(List<String> transportSerialIDs, Jedis jedis) {
        List<TransportCurrentLocationJrnl> currentLocationJrnls = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(transportSerialIDs)) {
            for (String transpSerialID : transportSerialIDs) {
                TransportCurrentLocationJrnl transportCurrentLocationJrnl = TransportCoordinateConverter.
                        convertTransportCoordinateToJrnl(transpSerialID, jedis.get(transpSerialID));
                currentLocationJrnls.add(transportCurrentLocationJrnl);
            }
        }
        return currentLocationJrnls;
    }
}
