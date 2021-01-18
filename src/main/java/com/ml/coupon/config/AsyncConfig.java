package com.ml.coupon.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    private static final int poolCapacity = 1000;

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(poolCapacity);
        executor.setMaxPoolSize(poolCapacity);
        executor.setQueueCapacity(poolCapacity);
        executor.setThreadNamePrefix("Async-");
        executor.initialize();
        return executor;
    }
}
