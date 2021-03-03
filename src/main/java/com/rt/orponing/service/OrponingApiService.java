// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service;

import com.rt.orponing.repository.data.*;
import com.rt.orponing.service.data.*;
import com.rt.orponing.service.interfaces.IStatus;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Service
@Lazy
@RequiredArgsConstructor
public class OrponingApiService implements IStatus {

    //region PrivateField;

    private final OrponingService orponingService;
    private final Map<UUID, TaskOrponing> mapTask = new HashMap<>();
    private final ExecutorService executor = new ThreadPoolExecutor(2, 4, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(10));
    private final Map<UUID, Future<?>> mapFuture = new HashMap<>();
    private final Object lock = new Object();
    private final Logger logger = LoggerFactory.getLogger(OrponingApiService.class);
    private Status status = Status.Stop(StatusMessage.STOP);

    //endregion PrivateField

    //region PublicMethod

    public String addTask(List<EntityAddress> entityAddressList) {
        start();

        UUID uuid = UUID.randomUUID();
        TaskOrponing taskOrponing = new TaskOrponing(uuid, entityAddressList);

        mapTask.put(uuid, taskOrponing);

        mapFuture.put(uuid, executor.submit(() -> {
            try {
                taskOrponing.startTask();
                logger.info("Start task id: " + taskOrponing.getId() + " Count address: " + taskOrponing.getListAddressRequest().size());

                List<AddressInfo> addressInfo = orponingService.orponingAddressList(taskOrponing.getListAddressRequest());
                orponingService.setAddressById(addressInfo);

                taskOrponing.setResult(addressInfo);

                logger.info("Stop task id: " + taskOrponing.getId());
                taskOrponing.stopTask();
            } catch (Exception e) {
                taskOrponing.errorTask(e.getMessage());
            }
        }));

        return uuid.toString();
    }

    public List<ResponseAddressInfo> getResultTask(String taskId) throws Exception {

        TaskOrponing taskOrponing = getTask(taskId);

        if (taskOrponing.getStatusTask().getStatus() == StatusType.COMPLETED) {
            return taskOrponing.getListAddressResponse();
        } else {
            throw new Exception("Task not completed");
        }
    }

    public Status getStatusTask(String taskId) throws Exception {
        return getTask(taskId).getStatusTask();
    }

    @Override
    public Status getStatus() {
        if (mapFuture.values().stream().anyMatch(f -> !f.isDone())) return status;

        mapFuture.clear();
        status = Status.Stop(StatusMessage.NO_WORK);
        return status;
    }

    public AddressInfo orponingAddress(EntityAddress entityAddress) {
        AddressInfo addressInfo = orponingService.orponingAddress(entityAddress);

        orponingService.setAddressById(addressInfo);

        return addressInfo;
    }

    //endregion PublicMethod

    //region PrivateMethod

    private void start() {
        synchronized (lock) {
            if (status.getStatus() != StatusType.START) {
                status = Status.Start(StatusMessage.START);
            }
        }
    }

    private UUID getId(String id) throws Exception {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new Exception("Id not correct");
        }
    }

    private TaskOrponing getTask(String id) throws Exception {
        UUID uuid = getId(id);
        boolean isContains = mapTask.containsKey(uuid);

        if (!isContains) throw new Exception("Id not found");

        return mapTask.get(uuid);
    }

    @PreDestroy
    private void end() {
        executor.shutdownNow();
    }

    //endregion PrivateMethod
}