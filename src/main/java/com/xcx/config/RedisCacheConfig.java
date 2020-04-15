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
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import com.xcx.core.commom.TedisCacheManager;

@EnableCaching
@Configuration
public class RedisCacheConfig extends CachingConfigurerSupport {
	public RedisCacheConfig() {
		super();
	}

	@Bean
	public CacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
		// 初始化一个RedisCacheWriter
        RedisCacheWriter cacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);

        // 设置默认过期时间：30 分钟
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(30))
                // .disableCachingNullValues()
                // 使用注解时的序列化、反序列化
                .serializeKeysWith(TedisCacheManager.STRING_PAIR)
                .serializeValuesWith(TedisCacheManager.FASTJSON_PAIR);

        // Map<String, RedisCacheConfiguration> caches = new HashMap<>();
        // // 缓存配置
        // RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
        //         .entryTtl(Duration.ofSeconds(60))
        //         .disableCachingNullValues()
        //         // .prefixKeysWith("redis.service")
        //         .serializeKeysWith(stringPair)
        //         .serializeValuesWith(fastJsonPair);
        // caches.put("redis.service", config);
        // return new TedisCacheManager(cacheWriter, defaultCacheConfig, caches);

        return new TedisCacheManager(cacheWriter, defaultCacheConfig);
        
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
