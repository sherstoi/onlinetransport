package com.jacklin.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.mongodb.MongoClient;
import io.dropwizard.Configuration;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import javax.ws.rs.ext.Provider;

/**
 * Created by iurii on 8/2/17.
 */
public class JacklinConfig extends Configuration {

    @JsonProperty
    public String mongohost = "localhost";

    @JsonProperty
    public int mongoport = 27017;

    @JsonProperty
    public String mongodb = "jakdojade_db";

}
