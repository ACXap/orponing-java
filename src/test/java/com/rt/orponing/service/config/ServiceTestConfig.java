package com.rt.orponing.service.config;

import com.rt.orponing.dao.interfaces.IDbAddress;
import com.rt.orponing.repository.IRepositoryOrpon;
import com.rt.orponing.service.OrponingApiService;
import com.rt.orponing.service.OrponingService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ServiceTestConfig {

    @Bean
    public OrponingService orponingService(IRepositoryOrpon repository, IDbAddress db ){
        return new OrponingService(repository, db);
    }

    @Bean
    public OrponingApiService orponingApiService(OrponingService service ){
        return new OrponingApiService(service);
    }
}