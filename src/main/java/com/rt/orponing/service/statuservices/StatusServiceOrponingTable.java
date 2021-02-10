// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service.statuservices;

import com.rt.orponing.service.OrponingTableService;
import com.rt.orponing.service.data.InfoService;
import com.rt.orponing.service.data.Status;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class StatusServiceOrponingTable extends StatusService {

    public StatusServiceOrponingTable(OrponingTableService ots) {
        this.ots = ots;
        infoService = new InfoService("Фоновый сервис орпонизации", "orponing-service", "server", "Сервис который по графику отслеживает наполнение таблицы с адресами и орпонизирует их", true);
    }

    private final OrponingTableService ots;

    @Override
    public Status getStatus() {
        status = ots.getStatus();

        return status;
    }
}