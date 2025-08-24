package ecoza.infra.cache;

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Profile;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

import java.time.Duration;

@Configuration
public class CacheConfig {

    public static final String ITEMS_CACHE = "items";

    @Bean
    @Profile("!test")
    public RedisCacheConfiguration cacheConfiguration() {
        // Configure cache entries to expire after 10 minutes and use JSON serialization.
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))
                .disableCachingNullValues()
                .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }

    @Bean
    @Profile("test")
    @ConditionalOnMissingBean(CacheManager.class)
    public CacheManager concurrentCacheManager() {
        return new ConcurrentMapCacheManager(ITEMS_CACHE, "itemsList");
    }
}