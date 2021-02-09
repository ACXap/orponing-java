// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service;

import com.rt.orponing.service.data.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ScheduledOrponingTableService implements IStartable {

    public ScheduledOrponingTableService(IStartable ots) {
        _ots = ots;
    }

    //region PrivateField
    private final Logger _logger = LoggerFactory.getLogger(ScheduledOrponingTableService.class);
    private final IStartable _ots;
    private Status _status;

    @Value("${background.orponing.service.auto.start}")
    private boolean _isAutoStart;

    //endregion PrivateField

    //region PublicProperty

    public Status getStatus() {
        return _status;
    }

    //endregion PublicProperty

    //region PublicMethod
    @Override
    public Status start() {
        if (_status.getStatus() == StatusType.START) return _status;

        _status = Status.Start(StatusMessage.START);
        startTask();

        return _status;
    }

    @Override
    public String getId() {
        return "scheduled-service";
    }
    //endregion PublicMethod

    //region PrivateMethod
    @PostConstruct
    private void init() {
        if (_isAutoStart) {
            _status = Status.Start(StatusMessage.START);
        } else {
            _status = Status.Stop(StatusMessage.STOP);
        }
    }

    @Scheduled(initialDelay = 10000, fixedRateString = "${background.orponing.service.delay}")
    private void startTask() {
         if (_status.getStatus() == StatusType.STOP) return;

        _logger.info("Start scheduled task");
        _ots.start();
    }
    //endregion PrivateMethod
}