package org.eatgo.collection.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
public class JedisConnectionProperties {

    @Bean
    public Jedis jedis() {
        return new Jedis("192.168.174.130",6379);
    }
}
