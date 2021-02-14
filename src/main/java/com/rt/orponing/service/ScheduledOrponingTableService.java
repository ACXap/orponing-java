// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service;

import com.rt.orponing.service.data.*;
import com.rt.orponing.service.interfaces.IStartable;
import com.rt.orponing.service.interfaces.IStatus;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class ScheduledOrponingTableService implements IStartable, IStatus {

    //region PrivateField
    private final IStartable ots;
    private final Logger logger = LoggerFactory.getLogger(ScheduledOrponingTableService.class);
    private Status status = Status.Stop(StatusMessage.STOP);

    //@Value("${background.orponing.service.auto.start}")
   // private boolean isAutoStart;

    //endregion PrivateField

    //region PublicMethod
    @Override
    public Status start() {
        if (status.getStatus() == StatusType.START) return status;

        status = Status.Start(StatusMessage.START);
        startTask();

        return status;
    }

    @Override
    public String getId() {
        return "scheduled-service";
    }

    @Override
    public Status getStatus() {
        return status;
    }
    //endregion PublicMethod

    //region PrivateMethod
//    @PostConstruct
//    private void init() {
//        status = isAutoStart ? Status.Start(StatusMessage.START) : Status.Stop(StatusMessage.STOP);
//    }

    @Scheduled(initialDelay = 10000, fixedRateString = "${background.orponing.service.delay}")
    private void startTask() {
        if (status.getStatus() == StatusType.STOP) return;

        logger.info("Start scheduled task");
        ots.start();
    }

    @Autowired
    private void setAutoStart(@Value("${background.orponing.service.auto.start}") boolean isAutoStart) {
        //this.isAutoStart = isAutoStart;
        status = isAutoStart ? Status.Start(StatusMessage.START) : Status.Stop(StatusMessage.STOP);
    }
    //endregion PrivateMethod
}