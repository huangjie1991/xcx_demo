package com.xcx.config;

import java.lang.reflect.Method;
import java.time.Duration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

@EnableCaching
@Configuration
public class RedisCacheConfig extends CachingConfigurerSupport {
	private volatile JedisConnectionFactory jedisConnectionFactory;
	private volatile RedisTemplate<String, String> redisTemplate;
	private volatile RedisCacheManager redisCacheManager;

	public RedisCacheConfig() {
		super();
	}

	/**
	 * 带参数的构造方法 初始化所有的成员变量
	 * 
	 * @param jedisConnectionFactory
	 * @param redisTemplate
	 * @param redisCacheManager
	 */
	public RedisCacheConfig(JedisConnectionFactory jedisConnectionFactory, RedisTemplate<String, String> redisTemplate,
			RedisCacheManager redisCacheManager) {
		this.jedisConnectionFactory = jedisConnectionFactory;
		this.redisTemplate = redisTemplate;
		this.redisCacheManager = redisCacheManager;
	}


	public RedisCacheManager getRedisCacheManager() {
		return redisCacheManager;
	}
	
	@Bean
	public CacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
		RedisSerializationContext.SerializationPair serializationPair =
				RedisSerializationContext.SerializationPair.fromSerializer(getRedisSerializer());
		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofSeconds(30))
				.serializeValuesWith(serializationPair);
		return RedisCacheManager
				.builder(RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory))
				.cacheDefaults(redisCacheConfiguration).build();
	}

  	private RedisSerializer<Object> getRedisSerializer(){
		return new GenericFastJsonRedisSerializer();
	}

	@Bean
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
			// 规定 本类名-方法名-参数名 为key(这个是没有自己指定key的时候，自己默认生成的)
			@Override
			public Object generate(Object o, Method method, Object... params) {
				StringBuilder sb = new StringBuilder();
				sb.append(o.getClass().getName());
				sb.append("-");
				sb.append(method.getName());
				sb.append("-");
				for (Object param : params) {
					sb.append(param.toString());
				}
				return sb.toString();
			}
		};
	}

}
