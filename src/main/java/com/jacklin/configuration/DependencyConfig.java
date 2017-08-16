package com.jacklin.configuration;

import com.google.inject.Singleton;
import com.jacklin.configuration.provider.JedisPoolProvider;
import com.jacklin.configuration.provider.MongoDatastoreProvider;
import com.jacklin.service.TransportCoordinateService;
import com.jacklin.service.TransportService;
import org.mongodb.morphia.Datastore;
import redis.clients.jedis.JedisPool;
import ru.vyarus.dropwizard.guice.module.support.DropwizardAwareModule;

/**
 * Created by iurii on 8/6/17.
 */
public class DependencyConfig extends DropwizardAwareModule {
    @Override
    public void configure() {
        bind(Datastore.class).toProvider(MongoDatastoreProvider.class).in(Singleton.class);
        bind(JedisPool.class).toProvider(JedisPoolProvider.class).in(Singleton.class);
        bind(TransportService.class).in(Singleton.class);
        bind(TransportCoordinateService.class).in(Singleton.class);
    }
}
