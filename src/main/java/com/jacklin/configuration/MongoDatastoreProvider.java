package com.jacklin.configuration;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 * Created by iurii on 8/8/17.
 */
@Singleton
public class MongoDatastoreProvider implements Provider<Datastore> {
    private JacklinConfig jacklinConfig;
    private MongoClient mongoClient;

    @Inject
    public MongoDatastoreProvider(JacklinConfig jacklinConfig, MongoClient mongoClient) {
        this.jacklinConfig = jacklinConfig;
        this.mongoClient = mongoClient;
    }

    public Datastore get() {
        return new Morphia().createDatastore(mongoClient, jacklinConfig.mongodb);
    }
}
