package com.jacklin;

import com.jacklin.configuration.DependencyConfig;
import com.jacklin.configuration.JacklinConfig;
import com.jacklin.configuration.healthcheck.JedisPoolHealthCheck;
import com.jacklin.configuration.healthcheck.MongoHealthCheck;
import com.jacklin.configuration.instance_manager.JedisPoolInstanceManager;
import com.jacklin.configuration.provider.JedisPoolProvider;
import com.jacklin.configuration.provider.MongoDatastoreProvider;
import com.jacklin.configuration.instance_manager.MongoInstanceManager;
import com.jacklin.job.CacheUploaderJob;
import com.jacklin.repository.TransportCoordinateJrnlDAO;
import com.jacklin.repository.TransportDAO;
import com.jacklin.rest.TransportCoordinateRest;
import com.jacklin.rest.TransportRest;
import com.jacklin.service.TransportCoordinateService;
import com.jacklin.service.TransportService;
import com.mongodb.MongoClient;
import io.dropwizard.Application;
import io.dropwizard.lifecycle.setup.ScheduledExecutorServiceBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.mongodb.morphia.Datastore;
import redis.clients.jedis.JedisPool;
import ru.vyarus.dropwizard.guice.GuiceBundle;
import ru.vyarus.dropwizard.guice.module.installer.CoreInstallersBundle;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by iurii on 7/31/17.
 */
public class Jacklin extends Application<JacklinConfig> {
    private static final String SERVER = "server";
    private static final String MONGO_HEALTH_CHECK = "mongoHealth";
    private static final String REDIS_HEALTH_CHECK = "redisHealth";
    private static final String PERIODICALY_TASK = "periodicaly_task";

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
        Datastore mongoDatastore = registerMongoDB(jacklinConfig, environment);
        //create Redis dependency
        JedisPool jedisPool = registerJedisPool(jacklinConfig, mongoDatastore, environment);
        //register uploading job
        registerScheduleTask(environment, jedisPool, mongoDatastore);
    }

    private Datastore registerMongoDB(JacklinConfig jacklinConfig, Environment environment) {
        MongoClient mongoClient = new MongoClient(jacklinConfig.mongohost, jacklinConfig.mongoport);
        MongoDatastoreProvider mongoDatastoreProvider = new MongoDatastoreProvider(jacklinConfig, mongoClient);
        Datastore datastore = mongoDatastoreProvider.get();

        environment.lifecycle().manage(new MongoInstanceManager(mongoClient));
        environment.healthChecks().register(MONGO_HEALTH_CHECK, new MongoHealthCheck(mongoClient));
        environment.jersey().register(new TransportRest(new TransportService(new TransportDAO(datastore))));

        return datastore;
    }

    private JedisPool registerJedisPool(JacklinConfig jacklinConfig, Datastore datastore, Environment environment) {
        JedisPoolProvider jedisPoolProvider = new JedisPoolProvider(jacklinConfig);
        JedisPool jedisPool = jedisPoolProvider.get();

        environment.lifecycle().manage(new JedisPoolInstanceManager(jedisPool));
        environment.healthChecks().register(REDIS_HEALTH_CHECK, new JedisPoolHealthCheck(jedisPool));
        environment.jersey().register(new TransportCoordinateRest(new TransportCoordinateService(jedisPool,
                new TransportCoordinateJrnlDAO(datastore))));

        return jedisPool;
    }

    private void registerScheduleTask(Environment environment, JedisPool jedisPool, Datastore mongoDatastore) {
        ScheduledExecutorServiceBuilder sesBuilder = environment.lifecycle().scheduledExecutorService(PERIODICALY_TASK);
        ScheduledExecutorService ses = sesBuilder.build();
        Runnable uploaderJob = new CacheUploaderJob(jedisPool, mongoDatastore);
        ses.scheduleWithFixedDelay(uploaderJob, 0, 30, TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws Exception {
        new Jacklin().run(SERVER);
    }
}