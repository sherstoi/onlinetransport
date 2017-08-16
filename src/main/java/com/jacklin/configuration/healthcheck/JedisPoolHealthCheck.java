package com.jacklin.configuration.healthcheck;

import com.codahale.metrics.health.HealthCheck;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by iurii on 8/16/17.
 */
public class JedisPoolHealthCheck extends HealthCheck {
    private static final String KEY = "HEALTH";
    private static final String VALUE = "test";

    private JedisPool jedisPool;

    public JedisPoolHealthCheck(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    protected Result check() throws Exception {
        try (Jedis client = jedisPool.getResource()) {
            client.set(KEY, VALUE);
            if (!VALUE.equals(client.get(KEY))) {
                return Result.unhealthy("Fetch redis fail!");
            }
        }
        return Result.healthy();
    }
}
