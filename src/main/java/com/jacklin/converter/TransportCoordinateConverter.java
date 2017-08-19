package com.jacklin.converter;

import com.jacklin.model.TransportCurrentLocation;
import com.jacklin.model.TransportCurrentLocationJrnl;
import org.bson.types.ObjectId;

/**
 * Created by iurii on 8/16/17.
 */
public class TransportCoordinateConverter {
    private static final int LATITUDE_INDEX = 0;
    private static final int LONGTITUDE_INDEX = 1;
    private static final String SPLIT_SYMBOL = ":";

    public static String convertTransportCoordinateToString(TransportCurrentLocation transportCurrentLocation) {
        return String.format("%d:%d", transportCurrentLocation.getLatitude(),
                                      transportCurrentLocation.getLongtitude());
    }

    public static TransportCurrentLocation convertTransportCoordinateToObject(String serialId, String longtitudeAndLatitude) {
        TransportCurrentLocation transportCurrentLocation = new TransportCurrentLocation();
        String[] latAndLongArr = longtitudeAndLatitude.split(SPLIT_SYMBOL);

        transportCurrentLocation.setTransportSerialID(serialId);
        transportCurrentLocation.setLatitude(Long.valueOf(latAndLongArr[LATITUDE_INDEX]));
        transportCurrentLocation.setLongtitude(Long.valueOf(latAndLongArr[LONGTITUDE_INDEX]));

        return transportCurrentLocation;
    }

    public static TransportCurrentLocationJrnl convertTransportCoordinateToJrnl(String serialId, String longtitudeAndLatitude) {
        TransportCurrentLocationJrnl transportCurrentLocationJrnl = new TransportCurrentLocationJrnl();
        String[] latAndLongArr = longtitudeAndLatitude.split(SPLIT_SYMBOL);

        transportCurrentLocationJrnl.setId(new ObjectId());
        transportCurrentLocationJrnl.setSerialId(serialId);
        transportCurrentLocationJrnl.setLatititude(Long.valueOf(latAndLongArr[LATITUDE_INDEX]));
        transportCurrentLocationJrnl.setLongtitude(Long.valueOf(latAndLongArr[LONGTITUDE_INDEX]));

        return transportCurrentLocationJrnl;
    }
}
