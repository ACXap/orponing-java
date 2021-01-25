package com.rt.orponing.service;

import com.google.common.collect.Lists;
import com.rt.orponing.dao.IDbSaveData;
import com.rt.orponing.repository.IRepositoryOrpon;
import com.rt.orponing.repository.data.AddressInfo;
import com.rt.orponing.repository.data.EntityAddress;
import com.rt.orponing.repository.data.RepositoryException;
import com.rt.orponing.service.data.StatusService;
import com.rt.orponing.service.data.StatusType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrponingService {

    public OrponingService(PropertyService propertyService, IRepositoryOrpon repository, IDbSaveData dbSaveData) {
        _propertyService = propertyService;
        _repository = repository;
        _dbSaveData = dbSaveData;

        _statusService = StatusService.Stop();
    }

    private final PropertyService _propertyService;
    private final IRepositoryOrpon _repository;
    private final IDbSaveData _dbSaveData;

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

        while (true) {
            try {
                List<EntityAddress> listEntityAddress = _dbSaveData.GetEntityAddress();

                if (listEntityAddress != null && !listEntityAddress.isEmpty()) {
                    System.out.println("Count address: " + listEntityAddress.size());

                    for (List<EntityAddress> list : Lists.partition(listEntityAddress, _propertyService.PartitionSizePars)) {

                        try {
                            List<AddressInfo> addressInfo = _repository.GetInfo(list);
                            _dbSaveData.AddAddressInfo(addressInfo);
                        } catch (RepositoryException fm) {
                            _dbSaveData.AddAddressInfoError(list, fm.getMessage());
                        }
                    }
                } else {
                    _statusService = StatusService.Stop();
                    break;
                }
            } catch (Exception ex) {
                _statusService = StatusService.Error(ex.getMessage());
                break;
            }
        }

        return _statusService;
    }
}