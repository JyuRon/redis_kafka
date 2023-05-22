package com.example.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.util.ObjectUtils;

@Configuration
@RequiredArgsConstructor
@Slf4j
@Profile("prod")
public class RedisProdConfig {

    @Value("${spring.profiles.active}")
    private String profile;
    @Value("${redis.sentinel.hostname}")
    private String hostname;
    @Value("${redis.sentinel.imageCount}")
    private int imageCount;
    @Value("${redis.sentinel.port}")
    private int port;
    @Bean
    public RedisConnectionFactory lettuceConnectionFactory() {
        if(ObjectUtils.isEmpty(hostname)){
            log.error("[RedisConfig][lettuceConnectionFactory] Not Valid HostName");
        }else if(ObjectUtils.isEmpty(imageCount)){
            log.error("[RedisConfig][lettuceConnectionFactory] Not Valid imageCount");
        }else if(ObjectUtils.isEmpty(port)){
            log.error("[RedisConfig][lettuceConnectionFactory] Not Valid portNumber");
        }

        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
                .master("mymaster");

        for (int i = 0; i < imageCount; i++) {
            String host = hostname + "-" + (i+1);
            sentinelConfig.addSentinel(new RedisNode(host, port));
        }

        sentinelConfig.setPassword(RedisPassword.of("1234"));
        return new LettuceConnectionFactory(sentinelConfig);
    }
}
