package com.jacklin.configuration.healthcheck;

import com.codahale.metrics.health.HealthCheck;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoIterable;

/**
 * Created by iurii on 8/5/17.
 */
public class MongoHealthCheck extends HealthCheck {
    private MongoClient mongoClient;

    public MongoHealthCheck(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Override
    protected Result check() throws Exception {
        try {
            MongoIterable mongoIterable = mongoClient.listDatabaseNames();
            System.out.println(mongoIterable.first());
            return Result.healthy();
        } catch (Exception ex) {
            return Result.unhealthy("Can not connect to DB...");
        }
    }

}
