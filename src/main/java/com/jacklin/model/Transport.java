package com.jacklin.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jacklin.enums.TransportType;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by iurii on 8/1/17.
 */
@Entity("transport")
public class Transport {
    @Id
    private String transportSerialID;
    @JsonProperty
    private Integer transportNumber;
    @JsonProperty
    private TransportType transportType;

    public String getTransportSerialID() {
        return transportSerialID;
    }

    public void setTransportSerialID(String transportSerialID) {
        this.transportSerialID = transportSerialID;
    }

    public Integer getTransportNumber() {
        return transportNumber;
    }

    public void setTransportNumber(Integer transportNumber) {
        this.transportNumber = transportNumber;
    }

    public TransportType getTransportType() {
        return transportType;
    }

    public void setTransportType(TransportType transportType) {
        this.transportType = transportType;
    }

    @Override
    public String toString() {
        return "Transport{" +
                "transportSerialID='" + transportSerialID + '\'' +
                ", transportNumber=" + transportNumber +
                ", transportType=" + transportType +
                '}';
    }
}