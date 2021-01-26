package com.rt.orponing.service;

import com.google.common.collect.Lists;
import com.rt.orponing.dao.IDbSaveData;
import com.rt.orponing.dao.data.DaoException;
import com.rt.orponing.repository.data.EntityAddress;
import com.rt.orponing.service.data.ResponseOrponingList;
import com.rt.orponing.service.data.StatusService;
import com.rt.orponing.service.data.StatusType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrponingTableService {

    public OrponingTableService(PropertyService propertyService, IDbSaveData dbSaveData, OrponinService service) {
        _propertyService = propertyService;
        _dbSaveData = dbSaveData;
        _service = service;

        _statusService = StatusService.Stop();
    }

    private final PropertyService _propertyService;
    private final IDbSaveData _dbSaveData;

    private final OrponinService _service;

    private final Object lock = new Object();
    private StatusService _statusService;

    public StatusService getStatusService() {
        return _statusService;
    }


    public StatusService startService() {

        synchronized (lock) {
            if (_statusService.Status == StatusType.START) {
                return _statusService;
            }
            _statusService = StatusService.Start();
        }

        while (_statusService.Status == StatusType.START) {

            try {
                List<EntityAddress> listEntityAddress = _dbSaveData.GetEntityAddress();

                if (listEntityAddress != null && !listEntityAddress.isEmpty()) {
                    for (List<EntityAddress> list : Lists.partition(listEntityAddress, _propertyService.PartitionSizePars)) {

                        ResponseOrponingList response = _service.OrponingAddressList(list);

                        _dbSaveData.AddAddressInfo(response.AddressInfoList);
                        _dbSaveData.AddAddressInfoError(response.AddressInfoError);
                    }
                } else {
                    _statusService = StatusService.Stop();
                }
            } catch (DaoException de) {
                _statusService = StatusService.Error(de.getMessage());
            }
        }

        return _statusService;
    }
}