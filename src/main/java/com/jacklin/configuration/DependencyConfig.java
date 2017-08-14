package com.jacklin.configuration;

import com.google.inject.Singleton;
import com.jacklin.service.TransportService;
import org.mongodb.morphia.Datastore;
import ru.vyarus.dropwizard.guice.module.support.DropwizardAwareModule;

/**
 * Created by iurii on 8/6/17.
 */
public class DependencyConfig extends DropwizardAwareModule {
    @Override
    public void configure() {
        bind(Datastore.class).toProvider(MongoDatastoreProvider.class).in(Singleton.class);
        bind(TransportService.class).in(Singleton.class);
    }
}
