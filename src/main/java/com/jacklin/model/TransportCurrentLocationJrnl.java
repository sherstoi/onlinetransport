package com.jacklin.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by iurii on 8/17/17.
 */
@Entity("transport_cur_location_jrnl")
public class TransportCurrentLocationJrnl {
    @Id
    private ObjectId id;
    @JsonProperty
    private String serialId;
    @JsonProperty
    private Long latititude;
    @JsonProperty
    private Long longtitude;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getSerialId() {
        return serialId;
    }

    public void setSerialId(String serialId) {
        this.serialId = serialId;
    }

    public Long getLatititude() {
        return latititude;
    }

    public void setLatititude(Long latititude) {
        this.latititude = latititude;
    }

    public Long getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(Long longtitude) {
        this.longtitude = longtitude;
    }

    @Override
    public String toString() {
        return "TransportCurrentLocationJrnl{" +
                "id=" + id +
                ", serialId='" + serialId + '\'' +
                ", latititude=" + latititude +
                ", longtitude=" + longtitude +
                '}';
    }
}
