package org.eatgo.collection.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class JedisConnectionProperties {

    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setJmxEnabled(false); // 👈 关键！
        config.setMaxTotal(20);
        config.setMaxIdle(10);
        config.setMinIdle(5);
        config.setTestOnBorrow(true);

        return new JedisPool(
                config,
                "192.168.174.130",
                6379,
                2000,           // timeout
                null,           // password
                false           // ✅ disableJmx = false
                // 其他参数使用默认值
        );
    }
}
