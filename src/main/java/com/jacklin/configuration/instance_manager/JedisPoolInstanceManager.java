package com.jacklin.configuration.instance_manager;

import io.dropwizard.lifecycle.Managed;
import redis.clients.jedis.JedisPool;

/**
 * Created by iurii on 8/16/17.
 */
public class JedisPoolInstanceManager implements Managed {
    private JedisPool jedisPool;

    public JedisPoolInstanceManager(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public void start() throws Exception {}

    @Override
    public void stop() throws Exception {
        jedisPool.destroy();
    }
}
