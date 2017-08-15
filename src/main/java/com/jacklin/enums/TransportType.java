package com.jacklin.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by iurii on 8/1/17.
 */
public enum TransportType {
    BUS, TRAM, MINIBUS;

    @JsonCreator
    public static TransportType fromValue(String value) {
        Optional<TransportType> transportTypeOptional = Arrays.stream(TransportType.class.getEnumConstants()).
                filter(e -> e.toString().equalsIgnoreCase(value)).findFirst();
        if (transportTypeOptional.isPresent()) {
            return transportTypeOptional.get();
        }

        throw new RuntimeException("Incorrect transport type was passed!");
    }
}
