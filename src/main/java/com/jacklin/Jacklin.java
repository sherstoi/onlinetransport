package com.jacklin;

import com.jacklin.configuration.DependencyConfig;
import com.jacklin.configuration.JacklinConfig;
import com.jacklin.configuration.MongoDatastoreProvider;
import com.jacklin.configuration.MongoInstanceManager;
import com.jacklin.healthcheck.MongoHealthCheck;
import com.jacklin.repository.TransportDAO;
import com.jacklin.rest.TransportRest;
import com.jacklin.service.TransportService;
import com.mongodb.MongoClient;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import ru.vyarus.dropwizard.guice.GuiceBundle;
import ru.vyarus.dropwizard.guice.module.installer.CoreInstallersBundle;

/**
 * Created by iurii on 7/31/17.
 */
public class Jacklin extends Application<JacklinConfig> {
    private static final String SERVER = "server";
    private static final String MONGO_HEALTH_CHECK = "mongoHealth";

    private GuiceBundle<JacklinConfig> guiceBundle;

    @Override
    public void initialize(Bootstrap<JacklinConfig> bootstrap) {
        guiceBundle = GuiceBundle.<JacklinConfig>builder()
                .modules(new DependencyConfig())
                .bundles(new CoreInstallersBundle())
                .build();

        bootstrap.addBundle(guiceBundle);
    }

    @Override
    public void run(JacklinConfig jacklinConfig, Environment environment) throws Exception {
        MongoClient mongoClient = new MongoClient(jacklinConfig.mongohost, jacklinConfig.mongoport);
        MongoDatastoreProvider mongoDatastoreProvider = new MongoDatastoreProvider(jacklinConfig, mongoClient);

        environment.lifecycle().manage(new MongoInstanceManager(mongoClient));
        environment.healthChecks().register(MONGO_HEALTH_CHECK, new MongoHealthCheck(mongoClient));
        environment.jersey().register(new TransportRest(new TransportService(new TransportDAO(mongoDatastoreProvider.get()))));
    }

    public static void main(String[] args) throws Exception {
        new Jacklin().run(SERVER);
    }
}
