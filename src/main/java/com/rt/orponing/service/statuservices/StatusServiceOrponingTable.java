// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service.statuservices;

import com.rt.orponing.service.OrponingTableService;
import com.rt.orponing.service.data.Status;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class StatusServiceOrponingTable extends StatusService{

    public StatusServiceOrponingTable(OrponingTableService ots) {
        _ots = ots;

        _name = "Фоновый сервис орпонизации";
        _id = "orponing-service";
        _icon= "server";
    }

    private final OrponingTableService _ots;

    @Override
   public Status getStatus() {
        _status = _ots.getStatusService();

        return _status;
    }
}