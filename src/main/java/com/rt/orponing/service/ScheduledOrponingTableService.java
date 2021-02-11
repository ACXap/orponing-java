// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service;

import com.rt.orponing.service.data.*;
import com.rt.orponing.service.interfaces.IStartable;
import com.rt.orponing.service.interfaces.IStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ScheduledOrponingTableService implements IStartable, IStatus {

    public ScheduledOrponingTableService(IStartable ots) {
        this.ots = ots;
    }

    //region PrivateField
    private final Logger logger = LoggerFactory.getLogger(ScheduledOrponingTableService.class);
    private final IStartable ots;
    private Status _status;

    @Value("${background.orponing.service.auto.start}")
    private boolean isAutoStart;

    //endregion PrivateField

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

    @Override
    public Status getStatus() {
        return _status;
    }
    //endregion PublicMethod

    //region PrivateMethod
    @PostConstruct
    private void init() {
        _status = isAutoStart ? Status.Start(StatusMessage.START) : Status.Stop(StatusMessage.STOP);
    }

    @Scheduled(initialDelay = 10000, fixedRateString = "${background.orponing.service.delay}")
    private void startTask() {
         if (_status.getStatus() == StatusType.STOP) return;

        logger.info("Start scheduled task");
        ots.start();
    }
    //endregion PrivateMethod
}