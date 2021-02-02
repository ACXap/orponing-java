// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service.statuservices;

import com.rt.orponing.service.ScheduledOrponingTableService;
import com.rt.orponing.service.data.InfoService;
import com.rt.orponing.service.data.Status;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class StatusServiceScheduled extends StatusService{

    public StatusServiceScheduled(ScheduledOrponingTableService sch){
        this.sch = sch;
        infoService = new InfoService("Планировщик запуска орпонизации", "scheduled-service", "clock", "", true);
    }

    private final ScheduledOrponingTableService sch;

    @Override
    public Status getStatus() {
        status = sch.getStatus();
        return status;
    }
}