package org.eatgo.menu.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Configuration
public class JedisConfig {

    @Bean
    public JedisPool jedisPool() {
        GenericObjectPoolConfig<Jedis> poolConfig = new GenericObjectPoolConfig<>();

        poolConfig.setJmxEnabled(false);
        // 设置最大连接数
        poolConfig.setMaxTotal(20);
        // 设置最大空闲连接数
        poolConfig.setMaxIdle(10);
        // 设置最小空闲连接数
        poolConfig.setMinIdle(2);
        // 当连接耗尽时，是否阻塞等待（true），还是抛出异常（false）
        poolConfig.setBlockWhenExhausted(true);
        // 当连接耗尽时，最大等待时间（毫秒）。这是关键！避免无限等待。
        poolConfig.setMaxWaitMillis(2000); // 等待2秒，超时则抛出异常
        // 在获取连接时测试其有效性
        poolConfig.setTestOnBorrow(true);
        // 在归还连接时测试其有效性
        poolConfig.setTestOnReturn(false); // 可选，根据性能要求
        // 后台线程检测可驱逐对象
        poolConfig.setTestWhileIdle(true);
        // 对象空闲多久后进行一次空闲对象清理（毫秒）
        poolConfig.setTimeBetweenEvictionRunsMillis(30000); // 30秒
        // 对象最小的空闲时间（毫秒），低于此值的空闲对象不会被清理
        poolConfig.setMinEvictableIdleTimeMillis(60000); // 60秒

        return new JedisPool(
                poolConfig,
                "192.168.174.130",
                6379,
                10000,
                null,
                false
        );
    }
}
