package com.rt.orponing.service;

import com.rt.orponing.service.data.Status;
import com.rt.orponing.service.data.StatusType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ScheduledOrponingTableService {

    public ScheduledOrponingTableService(IStartable ots){
        _ots = ots;
    }

    private final Logger _logger = LoggerFactory.getLogger(ScheduledOrponingTableService.class);
    private final IStartable _ots;
    private final Object _lock = new Object();
    private Status _status;

    @Value("${background.orponing.service.auto.start}")
    private boolean _isAutoStart;

    @Scheduled(fixedRateString = "${background.orponing.service.delay}")
    void start() {
        if(_status.getStatus() == StatusType.STOP) return;

        _logger.info("Start scheduled task");
        _ots.start();
    }

    public Status getStatus(){
        return _status;
    }

    @PostConstruct
    private void init(){
        if(_isAutoStart) {
            _status = Status.Start(Status.StatusMessage.START);
        }
        else {
            _status = Status.Stop(Status.StatusMessage.STOP);
        }
    }
}