package com.rt.orponing.service;

import com.rt.orponing.service.data.Status;
import com.rt.orponing.service.data.StatusType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

@Component
public class ScheduledOrponingTableService {

    public ScheduledOrponingTableService(OrponingTableService ots){
        _ots = ots;
    }

    private final OrponingTableService _ots;
    private final Object _lock = new Object();
    private Status _status;

   // @Value("${background.orponing.service.auto.start}")
    //private boolean _isStart;

    @Scheduled(fixedRateString = "${background.orponing.service.delay}")
    void checkVehicle() {
        if(_status.getStatus() == StatusType.STOP) return;

        System.out.println(new Date());


        //_ots.startService();
    }

    public Status getStatus(){
        return _status;
    }

    @PostConstruct
    private void init(@Value("${background.orponing.service.auto.start}") boolean isStart){
        if(isStart) {
            _status = Status.Start(Status.StatusMessage.START);
        }
        else {
            _status = Status.Stop(Status.StatusMessage.STOP);
        }
    }
}