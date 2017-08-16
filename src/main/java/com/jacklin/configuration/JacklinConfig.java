package com.jacklin.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

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

    @JsonProperty
    public String redisHost = "localhost";

    @JsonProperty
    public int redisPort = 6379;
}
