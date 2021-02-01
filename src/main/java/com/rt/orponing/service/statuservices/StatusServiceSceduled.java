package com.rt.orponing.service.statuservices;

import com.rt.orponing.service.ScheduledOrponingTableService;
import com.rt.orponing.service.data.Status;
import org.springframework.stereotype.Component;

@Component
public class StatusServiceSceduled extends StatusService{

    public StatusServiceSceduled(ScheduledOrponingTableService sch){
        _sch = sch;

        _name = "Планировщик запуска орпонизации";
        _id = "scheduled-service";
        _icon ="clock";
    }

    private final ScheduledOrponingTableService _sch;

    @Override
    public Status getStatus() {
        _status = _sch.getStatus();
        return _status;
    }
}