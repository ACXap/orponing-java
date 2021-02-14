// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service;

import com.google.common.collect.Lists;
import com.rt.orponing.dao.interfaces.IDbSaveData;
import com.rt.orponing.dao.data.DaoException;
import com.rt.orponing.repository.data.*;
import com.rt.orponing.service.data.*;
import com.rt.orponing.service.interfaces.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class OrponingTableService implements IStartable, IStatus {

    //region PrivateField

    private final IDbSaveData dbSaveData;
    private final OrponingService service;
    private final Logger logger = LoggerFactory.getLogger(OrponingTableService.class);
    private final Object lock = new Object();
    @Getter
    private final String id = "orponing-service";
    @Value("${db.partition.size.record}")
    private int partitionSize;
    @Getter
    private Status status = Status.Stop(StatusMessage.STOP);

    //endregion PrivateField

    //region PublicMethod

    @Override
    public Status start() {
        if (status.getStatus() == StatusType.START) return status;

        synchronized (lock) {
            if (status.getStatus() == StatusType.START) return status;
            status = Status.Start(StatusMessage.START);
        }

        ExecutorService service = Executors.newSingleThreadExecutor();

        service.execute(this::run);

        service.shutdown();

        return status;
    }

    //endregion PublicMethod

    //region PrivateMethod

    private void run() {
        logger.info("Start service orponing");
        while (status.getStatus() == StatusType.START) {
            try {
                List<EntityAddress> listEntityAddress = dbSaveData.GetEntityAddress();

                if (!listEntityAddress.isEmpty()) {

                    for (List<EntityAddress> list : Lists.partition(listEntityAddress, partitionSize)) {
                        List<AddressInfo> response = service.orponingAddressList(list);
                        service.setAddressById(response);
                        dbSaveData.UpdateEntityAddress(response);
                    }
                } else {
                    logger.info("Not found address for orponing");
                    status = Status.Stop(StatusMessage.NO_WORK);
                }
            } catch (DaoException de) {
                logger.error(de.getMessage());
                status = Status.Error(StatusMessage.ERROR + ". " + de.getMessage());
            }
        }
    }

    //endregion PrivateMethod
}