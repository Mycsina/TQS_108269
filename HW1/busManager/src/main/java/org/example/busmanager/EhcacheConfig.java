package org.example.busmanager;

import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.example.busmanager.entity.CurrencyResponse;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.time.Duration;

@Configuration
@EnableCaching
public class EhcacheConfig {

    @Bean
    public CacheManager createCacheWithinManager() {
        // creation of cache configuration
        CacheConfiguration<SimpleKey, CurrencyResponse> cacheConfiguration = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(SimpleKey.class, CurrencyResponse.class,
                        ResourcePoolsBuilder
                                .heap(1000)
                                .build())
                .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofHours(1)))
                .build();

        CachingProvider cachingProvider = Caching.getCachingProvider();
        CacheManager cacheManager = cachingProvider.getCacheManager();

        javax.cache.configuration.Configuration<SimpleKey, CurrencyResponse> configuration = Eh107Configuration.fromEhcacheCacheConfiguration(cacheConfiguration);

        cacheManager.createCache("currencyCache", configuration);

        Runtime.getRuntime().addShutdownHook(new Thread(cacheManager::close));

        return cacheManager;
    }
}