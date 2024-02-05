package com.cheese.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author WEI CHEN GUANG
 * @date 2024/1/29 11:23
 * @projectName cheese-takeaway
 */
@Configuration
@Slf4j
public class RedisConfiguration
{
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        log.info("开始创建redis模板对象...");
        RedisTemplate redisTemplate = new RedisTemplate<>();
        //设置redis得连接工厂对象
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //设置redis key得序列化器
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
