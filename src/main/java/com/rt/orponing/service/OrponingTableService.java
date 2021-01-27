package com.rt.orponing.service;

import com.google.common.collect.Lists;
import com.rt.orponing.dao.IDbSaveData;
import com.rt.orponing.dao.data.DaoException;
import com.rt.orponing.repository.data.AddressInfo;
import com.rt.orponing.repository.data.EntityAddress;
import com.rt.orponing.service.data.StatusService;
import com.rt.orponing.service.data.StatusType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Component
public class OrponingTableService {

    public OrponingTableService(PropertyService propertyService, IDbSaveData dbSaveData, OrponingService service) {
        _propertyService = propertyService;
        _dbSaveData = dbSaveData;
        _service = service;

        _statusService = StatusService.Stop();
    }

    //region PrivateField
    private final PropertyService _propertyService;
    private final IDbSaveData _dbSaveData;
    private final OrponingService _service;
    private final Logger _logger = LoggerFactory.getLogger("OrponingTableService");

    private final Object lock = new Object();
    private StatusService _statusService;
    //endregion PrivateField

    //region PublicProperty
    public StatusService getStatusService() {
        return _statusService;
    }
    //endregion PublicProperty

    //region PublicMethod
    public StatusService startService() {
        _logger.info("Start service orponing");

        synchronized (lock) {
            if (_statusService.Status == StatusType.START) {
                return _statusService;
            }
            _statusService = StatusService.Start();
        }

        ExecutorService service = Executors.newCachedThreadPool();

        service.execute(() -> {
            while (_statusService.Status == StatusType.START) {
                try {
                    _logger.info("Load address for orponing");
                    List<EntityAddress> listEntityAddress = _dbSaveData.GetEntityAddress();

                    if (listEntityAddress != null && !listEntityAddress.isEmpty()) {

                        for (List<EntityAddress> list : Lists.partition(listEntityAddress, _propertyService.PartitionSizePars)) {
                            _logger.info("Orponing address");
                            List<AddressInfo> response = _service.OrponingAddressList(list);

                            _logger.info("Write address info to bd");
                            List<AddressInfo> address = response.stream().filter(a -> a.IsValid).collect(Collectors.toList());
                            _dbSaveData.AddAddressInfo(address);

                            List<AddressInfo> errorAddress = response.stream().filter(a -> !a.IsValid).collect(Collectors.toList());
                            if (!errorAddress.isEmpty()) {
                                _logger.info("Write address info error to bd");
                                _dbSaveData.AddAddressInfoError(errorAddress);
                            }
                        }
                    } else {
                        _logger.info("Not found address for orponing");
                        _statusService = StatusService.Stop();
                    }
                } catch (DaoException de) {
                    _logger.error(de.getMessage());
                    _statusService = StatusService.Error(de.getMessage());
                }
            }
        });

        service.shutdown();

        return _statusService;
    }

    //endregion PublicMethod
}