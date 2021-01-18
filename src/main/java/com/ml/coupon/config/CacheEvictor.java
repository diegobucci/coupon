package com.ml.coupon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CacheEvictor {

    @Autowired
    private CacheManager cacheManager;

    @Scheduled(fixedRateString = "${spring.cache.evictiontime}")
    public void evictsAllCache() {
        cacheManager.getCacheNames().stream()
                .forEach(cacheName -> cacheManager.getCache(cacheName).clear());
    }
}
