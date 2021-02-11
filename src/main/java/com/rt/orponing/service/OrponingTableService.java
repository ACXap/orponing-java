// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service;

import com.google.common.collect.Lists;
import com.rt.orponing.dao.interfaces.IDbSaveData;
import com.rt.orponing.dao.data.DaoException;
import com.rt.orponing.repository.data.*;
import com.rt.orponing.service.data.*;
import com.rt.orponing.service.interfaces.IStartable;
import com.rt.orponing.service.interfaces.IStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Lazy
public class OrponingTableService implements IStartable, IStatus {

    public OrponingTableService(IDbSaveData dbSaveData, OrponingService service) {
        _dbSaveData = dbSaveData;
        _service = service;
        _status = Status.Stop(StatusMessage.STOP);
    }

    //region PrivateField
    private final IDbSaveData _dbSaveData;
    private final OrponingService _service;
    private final Logger logger = LoggerFactory.getLogger(OrponingTableService.class);
    private final Object lock = new Object();

    @Value("${db.partition.size.record}")
    private int _partitionSize;

    private Status _status;
    //endregion PrivateField

    //region PublicProperty
    @Override
    public Status getStatus() {
        return _status;
    }
    //endregion PublicProperty

    //region PublicMethod

    @Override
    public Status start() {
        if (_status.getStatus() == StatusType.START) return _status;

        synchronized (lock) {
            if (_status.getStatus() == StatusType.START) {
                return _status;
            }
            _status = Status.Start(StatusMessage.START);
        }

        ExecutorService service = Executors.newSingleThreadExecutor();

        logger.info("Start service orponing");
        service.execute(this::run);

        service.shutdown();

        return _status;
    }

    @Override
    public String getId() {
        return "orponing-service";
    }

    private void run() {
        while (_status.getStatus() == StatusType.START) {
            try {
                List<EntityAddress> listEntityAddress = _dbSaveData.GetEntityAddress();

                if (listEntityAddress != null && !listEntityAddress.isEmpty()) {

                    for (List<EntityAddress> list : Lists.partition(listEntityAddress, _partitionSize)) {
                        List<AddressInfo> response = _service.OrponingAddressList(list);
                        _service.setAddressById(response);
                        _dbSaveData.UpdateEntityAddress(response);
                    }
                } else {
                    logger.info("Not found address for orponing");
                    _status = Status.Stop(StatusMessage.NO_WORK);
                }
            } catch (DaoException de) {
                logger.error(de.getMessage());
                _status = Status.Error(StatusMessage.ERROR + ". " + de.getMessage());
            }
        }
    }

    //endregion PublicMethod
}