
package com.asp.common.redis;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableCaching
@PropertySource("classpath:redis.properties")
public class RedisConfig {
	@Value("${redis.hostName}")
	private String redisHostName;

	@Value("${redis.password}")
	private String redisPassowrd;

	@Value("${redis.ignorePassword}")
	private boolean ignorePassword;

	@Value("${redis.port}")
	private int redisPort;

	@Value("${redis.pool.maxActive}")
	private int maxTotal;

	@Value("${redis.pool.maxIdle}")
	private int maxIdle;

	@Value("${redis.pool.maxWait}")
	private int maxWaitMillis;

	@Value("${redis.pool.testOnBorrow}")
	private boolean testOnBorrow;

	private static final Logger LOGGER = Logger.getLogger(RedisConfig.class);

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		// Required to read from redis.properties and inject into the data
		// members
		// annnotated with @Value.
		PropertySourcesPlaceholderConfigurer propConfigurer = new PropertySourcesPlaceholderConfigurer();
		// Set any additional properties here.

		// The xml configuration had ignoreUnresolvablePlaceholders set to true.
		// Removed it
		// to make the spring context loading to fail, if an expected property
		// is not found.
		return propConfigurer;
	}

	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory factory = new JedisConnectionFactory();
		factory.setHostName(redisHostName);
		factory.setPort(redisPort);
		factory.setUsePool(true);
		factory.setPoolConfig(jedisPoolConfig());
		if (!ignorePassword) {
			String rp = redisPassowrd;
			factory.setPassword(rp);
		}
		return factory;
	}

	@Bean
	JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(maxTotal);
		jedisPoolConfig.setMaxIdle(maxIdle);
		jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
		jedisPoolConfig.setTestOnBorrow(testOnBorrow);
		return jedisPoolConfig;
	}

	@Bean
	RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(jedisConnectionFactory());
		
		return template;
	}

	@Bean
	CacheManager redisCacheManager() {
		RedisCacheManagerBuilder builder = RedisCacheManagerBuilder.fromConnectionFactory(jedisConnectionFactory());
		RedisCacheManager redisCacheManager = builder.build();
		return redisCacheManager;
	}
}