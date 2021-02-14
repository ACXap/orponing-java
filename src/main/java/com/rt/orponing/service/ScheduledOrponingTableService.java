// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service;

import com.rt.orponing.service.data.*;
import com.rt.orponing.service.interfaces.IStartable;
import com.rt.orponing.service.interfaces.IStatus;
import lombok.Getter;
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
    @Getter
    private final String id = "scheduled-service";
    @Getter
    private Status status;

    //endregion PrivateField

    //region PublicMethod

    @Override
    public Status start() {
        if (status.getStatus() == StatusType.START) return status;

        status = Status.Start(StatusMessage.START);
        startTask();

        return status;
    }

    //endregion PublicMethod

    //region PrivateMethod
    @Autowired
    private void setStatus(@Value("${background.orponing.service.auto.start}") boolean isAutoStart) {
        status = isAutoStart ? Status.Start(StatusMessage.START) : Status.Stop(StatusMessage.STOP);
    }

    @Scheduled(initialDelay = 10000, fixedRateString = "${background.orponing.service.delay}")
    private void startTask() {
        if (status == null || status.getStatus() == StatusType.STOP) return;

        logger.info("Start scheduled task");
        ots.start();
    }
    //endregion PrivateMethod
}