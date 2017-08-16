package com.jacklin.configuration.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.jacklin.configuration.JacklinConfig;
import redis.clients.jedis.JedisPool;

/**
 * Created by iurii on 8/16/17.
 */
@Singleton
public class JedisPoolProvider implements Provider<JedisPool> {
    private JacklinConfig jacklinConfig;

    @Inject
    public JedisPoolProvider(JacklinConfig jacklinConfig) {
        this.jacklinConfig = jacklinConfig;
    }

    public JedisPool get() {return new JedisPool(jacklinConfig.redisHost, jacklinConfig.redisPort);}
}
