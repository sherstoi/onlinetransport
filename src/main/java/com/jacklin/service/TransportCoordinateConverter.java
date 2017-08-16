package com.jacklin.service;

import com.jacklin.model.TransportCurrentLocation;

/**
 * Created by iurii on 8/16/17.
 */
public class TransportCoordinateConverter {
    private static final int LATITUDE_INDEX = 0;
    private static final int LONGTITUDE_INDEX = 1;
    private static final String SPLIT_SYMBOL = ":";

    static String convertTransportCoordinateToString(TransportCurrentLocation transportCurrentLocation) {
        return String.format("%d:%d", transportCurrentLocation.getLatitude(),
                                      transportCurrentLocation.getLongtitude());
    }

    static TransportCurrentLocation convertTransportCoordinateToObject(String serialId, String longtitudeAndLatitude) {
        TransportCurrentLocation transportCurrentLocation = new TransportCurrentLocation();
        String[] latAndLongArr = longtitudeAndLatitude.split(SPLIT_SYMBOL);

        transportCurrentLocation.setTransportSerialID(serialId);
        transportCurrentLocation.setLatitude(Long.valueOf(latAndLongArr[LATITUDE_INDEX]));
        transportCurrentLocation.setLongtitude(Long.valueOf(latAndLongArr[LONGTITUDE_INDEX]));

        return transportCurrentLocation;
    }
}
