// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service.statuservices.config;

import com.rt.orponing.dao.DbMdm;
import com.rt.orponing.dao.DbOrpon;
import com.rt.orponing.service.*;
import com.rt.orponing.service.data.InfoService;
import com.rt.orponing.service.statuservices.StatusService;
import com.rt.orponing.service.statuservices.StatusServiceDb;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class StatusServiceConfig {

    @Bean
    public StatusServiceDb statusServiceDbOrpon(DbOrpon db, @Qualifier("infoServiceDbOrpon") InfoService infoService){
        return new StatusServiceDb(db, infoService);
    }

    @Bean
    public StatusServiceDb statusServiceDbMdm(DbMdm db, @Qualifier("infoServiceDbMdm") InfoService infoService){
        return new StatusServiceDb(db, infoService);
    }

    @Bean
    @Lazy
    public StatusService statusServiceOrponingApi(OrponingApiService oas, @Qualifier("infoServiceOrponingApi") InfoService infoService){
        return new StatusService(oas, infoService);
    }

    @Bean
    public StatusService statusServiceOrponingTable(OrponingTableService oas, @Qualifier("infoServiceOrponingTable") InfoService infoService){
        return new StatusService(oas, infoService);
    }

    @Bean
    public StatusService statusServiceOrponService(OrponingService oas, @Qualifier("infoServiceOrponService") InfoService infoService){
        return new StatusService(oas, infoService);
    }

    @Bean
    public StatusService statusServiceScheduled(ScheduledOrponingTableService oas, @Qualifier("infoServiceScheduled") InfoService infoService){
        return new StatusService(oas, infoService);
    }
}