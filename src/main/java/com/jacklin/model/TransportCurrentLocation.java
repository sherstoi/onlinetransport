package com.jacklin.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by iurii on 8/1/17.
 */
@Entity("transport_current_location")
public class TransportCurrentLocation {
    @Id
    private String transportSerialID;
    @JsonProperty
    private Long latitude;
    @JsonProperty
    private Long longtitude;

    public String getTransportSerialID() {
        return transportSerialID;
    }

    public void setTransportSerialID(String transportSerialID) {
        this.transportSerialID = transportSerialID;
    }

    public Long getLatitude() {
        return latitude;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    public Long getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(Long longtitude) {
        this.longtitude = longtitude;
    }

    @Override
    public String toString() {
        return "TransportCurrentLocation{" +
                "transportSerialID='" + transportSerialID + '\'' +
                ", latitude=" + latitude +
                ", longtitude=" + longtitude +
                '}';
    }
}
