package com.rt.orponing.service.statuservices;

import com.rt.orponing.service.ScheduledOrponingTableService;
import com.rt.orponing.service.data.Status;
import org.springframework.stereotype.Component;

@Component
public class StatusServiceScheduled extends StatusService{

    public StatusServiceScheduled(ScheduledOrponingTableService sch){
        _sch = sch;

        _name = "Планировщик запуска орпонизации";
        _id = "scheduled-service";
        _icon ="clock";
        _isStartable = true;
    }

    private final ScheduledOrponingTableService _sch;

    @Override
    public Status getStatus() {
        _status = _sch.getStatus();
        return _status;
    }
}