package com.metanet.amatmu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
public class RedisConfig implements CachingConfigurer {

  @Value("${spring.data.redis.port}")
  private int port;
  @Value("${spring.data.redis.host}")
  private String host;

  @Bean
  LettuceConnectionFactory lettuceConnectionFactory() {
    LettuceClientConfiguration lettuceClientConfiguration = LettuceClientConfiguration.builder()
        .build();

    RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(
        host, port);

    return new LettuceConnectionFactory(redisStandaloneConfiguration, lettuceClientConfiguration);
  }

  @Bean
  RedisTemplate<?, ?> redisTemplate() {
    RedisTemplate<String, String> template = new RedisTemplate<>();
    template.setValueSerializer(new StringRedisSerializer());
    template.setKeySerializer(new StringRedisSerializer());
    template.setConnectionFactory(lettuceConnectionFactory());
    return template;
  }

}
