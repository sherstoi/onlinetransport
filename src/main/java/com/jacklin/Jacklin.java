package com.jacklin;

import com.jacklin.configuration.DependencyConfig;
import com.jacklin.configuration.JacklinConfig;
import com.jacklin.configuration.healthcheck.JedisPoolHealthCheck;
import com.jacklin.configuration.healthcheck.MongoHealthCheck;
import com.jacklin.configuration.instance_manager.JedisPoolInstanceManager;
import com.jacklin.configuration.provider.JedisPoolProvider;
import com.jacklin.configuration.provider.MongoDatastoreProvider;
import com.jacklin.configuration.instance_manager.MongoInstanceManager;
import com.jacklin.repository.TransportDAO;
import com.jacklin.rest.TransportCoordinateRest;
import com.jacklin.rest.TransportRest;
import com.jacklin.service.TransportCoordinateService;
import com.jacklin.service.TransportService;
import com.mongodb.MongoClient;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import redis.clients.jedis.JedisPool;
import ru.vyarus.dropwizard.guice.GuiceBundle;
import ru.vyarus.dropwizard.guice.module.installer.CoreInstallersBundle;

/**
 * Created by iurii on 7/31/17.
 */
public class Jacklin extends Application<JacklinConfig> {
    private static final String SERVER = "server";
    private static final String MONGO_HEALTH_CHECK = "mongoHealth";
    private static final String REDIS_HEALTH_CHECK = "redisHealth";

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
        //create MongoDB dependency
        registerMongoDB(jacklinConfig, environment);
        //create Redis dependency
        registerJedisPool(jacklinConfig, environment);
    }

    private void registerMongoDB(JacklinConfig jacklinConfig, Environment environment) {
        MongoClient mongoClient = new MongoClient(jacklinConfig.mongohost, jacklinConfig.mongoport);
        MongoDatastoreProvider mongoDatastoreProvider = new MongoDatastoreProvider(jacklinConfig, mongoClient);

        environment.lifecycle().manage(new MongoInstanceManager(mongoClient));
        environment.healthChecks().register(MONGO_HEALTH_CHECK, new MongoHealthCheck(mongoClient));
        environment.jersey().register(new TransportRest(new TransportService(new TransportDAO(mongoDatastoreProvider.get()))));
    }

    private void registerJedisPool(JacklinConfig jacklinConfig, Environment environment) {
        JedisPoolProvider jedisPoolProvider = new JedisPoolProvider(jacklinConfig);
        JedisPool jedisPool = jedisPoolProvider.get();

        environment.lifecycle().manage(new JedisPoolInstanceManager(jedisPool));
        environment.healthChecks().register(REDIS_HEALTH_CHECK, new JedisPoolHealthCheck(jedisPool));
        environment.jersey().register(new TransportCoordinateRest(new TransportCoordinateService(jedisPool)));
    }

    public static void main(String[] args) throws Exception {
        new Jacklin().run(SERVER);
    }
}