package com.rt.orponing.service.config;

import com.rt.orponing.service.LogReadService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration

public class LogReadServiceConfig {
    @Bean
    public LogReadService logReadService(){
        return new LogReadService();
    }
}