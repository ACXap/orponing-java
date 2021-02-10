package com.rt.orponing.dao;

import com.rt.orponing.dao.data.DbConnect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbConnectConfig {

    @Bean
    public DbConnect dbConnectSaveData(@Value("${db.url}") String url, @Value("${db.user}") String user, @Value("${db.password}") String password) {
        return new DbConnect(url, user, password);
    }

    @Bean
    public DbConnect dbConnectAddress(@Value("${db.orpon.url}") String url, @Value("${db.orpon.user}") String user, @Value("${db.orpon.password}") String password) {
        return new DbConnect(url, user, password);
    }
}