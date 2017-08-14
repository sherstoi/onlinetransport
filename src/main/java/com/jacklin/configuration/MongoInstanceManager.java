package com.jacklin.configuration;

import com.mongodb.MongoClient;
import io.dropwizard.lifecycle.Managed;

/**
 * Created by iurii on 8/5/17.
 */
public class MongoInstanceManager implements Managed {
    private MongoClient mongoClient;

    public MongoInstanceManager(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Override
    public void start() throws Exception {
    }

    @Override
    public void stop() throws Exception {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
