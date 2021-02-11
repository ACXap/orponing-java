// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service.statuservices.config;

import com.rt.orponing.service.data.InfoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class InfoServiceConfig {

    @Bean
    public InfoService infoServiceOrponingApi() {
        return new InfoService("Фоновый сервис API",
                "orponing-service-api",
                "server",
                "Все обрабатываемые файлы файлы попадают в очередь и обрабатываются поочереди, данный сервис обрабатывает эту очередь",
                false);
    }

    @Bean
    public InfoService infoServiceDbMdm() {
        return new InfoService("БД для хранения разобранных адресов",
                "db",
                "database",
                "БД где хранятся адреса для орпонизации",
                false);
    }

    @Bean
    public InfoService infoServiceDbOrpon() {
        return new InfoService("БД со справочником ГИД - Адрес",
                "db-address",
                "database",
                "БД со справочником ГИД - Адрес",
                false);
    }

    @Bean
    public InfoService infoServiceOrponingTable() {
        return new InfoService("Фоновый сервис орпонизации",
                "orponing-service",
                "server",
                "Сервис который по графику отслеживает наполнение таблицы с адресами и орпонизирует их",
                true);
    }

    @Bean
    public InfoService infoServiceOrponService() {
        return new InfoService("Сервис для разбора адресов",
                "orponing",
                "server",
                "Основной сервис который по адресу предоставляет информацию",
                false);
    }

    @Bean
    public InfoService infoServiceScheduled() {
        return new InfoService("Планировщик запуска орпонизации",
                "scheduled-service",
                "clock",
                "Планировщик запуска обработки таблицы с адресами",
                true);
    }
}