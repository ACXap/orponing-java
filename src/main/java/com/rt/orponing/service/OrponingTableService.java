// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service;

import com.google.common.collect.Lists;
import com.rt.orponing.dao.IDbSaveData;
import com.rt.orponing.dao.data.DaoException;
import com.rt.orponing.repository.data.AddressInfo;
import com.rt.orponing.repository.data.EntityAddress;
import com.rt.orponing.service.data.Status;
import com.rt.orponing.service.data.StatusType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class OrponingTableService {

    public OrponingTableService(PropertyService propertyService, IDbSaveData dbSaveData, OrponingService service) {
        _propertyService = propertyService;
        _dbSaveData = dbSaveData;
        _service = service;

        _status = Status.Stop(Status.StatusMessage.STOP);
    }

    //region PrivateField
    private final PropertyService _propertyService;
    private final IDbSaveData _dbSaveData;
    private final OrponingService _service;
    private final Logger _logger = LoggerFactory.getLogger("OrponingTableService");

    private final Object lock = new Object();
    private Status _status;
    //endregion PrivateField

    //region PublicProperty
    public Status getStatusService() {
        return _status;
    }
    //endregion PublicProperty

    //region PublicMethod
    public Status startService() {
        _logger.info("Start service orponing");

        synchronized (lock) {
            if (_status.getStatus() == StatusType.START) {
                return _status;
            }
            _status = Status.Start(Status.StatusMessage.START);
        }

        ExecutorService service = Executors.newCachedThreadPool();

        service.execute(() -> {
            while (_status.getStatus() == StatusType.START) {
                try {
                    _logger.info("Load address for orponing");
                    List<EntityAddress> listEntityAddress = _dbSaveData.GetEntityAddress();

                    if (listEntityAddress != null && !listEntityAddress.isEmpty()) {

                        for (List<EntityAddress> list : Lists.partition(listEntityAddress, _propertyService.PartitionSizePars)) {
                            _logger.info("Orponing collection address");
                            List<AddressInfo> response = _service.OrponingAddressList(list);

                            _logger.info("Write collection address info to bd");
                            _dbSaveData.AddAddressInfo(response);
                        }
                    } else {
                        _logger.info("Not found address for orponing");
                        _status = Status.Stop(Status.StatusMessage.NO_WORK);
                    }
                } catch (DaoException de) {
                    _logger.error(de.getMessage());
                    _status = Status.Error(Status.StatusMessage.ERROR + ". " + de.getMessage());
                }
            }
        });

        service.shutdown();

        return _status;
    }

    //endregion PublicMethod
}