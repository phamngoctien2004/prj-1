package com.web.prj.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {
    @Bean(name = "emailExecutor")
    public Executor emailExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); // số luồng core
        executor.setMaxPoolSize(10); // số luồng tối đa tạo ra
        executor.setQueueCapacity(500); // hàng đợi tối đa
        executor.setThreadNamePrefix("EmailSender-");
        executor.initialize();
        return executor;
    }
}
