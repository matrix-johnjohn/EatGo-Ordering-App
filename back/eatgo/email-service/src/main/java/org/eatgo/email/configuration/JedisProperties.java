package org.eatgo.email.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
public class JedisProperties {

    @Bean
    public Jedis jedis(){
        return new Jedis("192.168.174.130", 6379);
    };
}
