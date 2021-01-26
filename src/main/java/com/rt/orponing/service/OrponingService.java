package com.rt.orponing.service;

import com.google.common.collect.Lists;
import com.rt.orponing.repository.IRepositoryOrpon;
import com.rt.orponing.repository.data.*;
import com.rt.orponing.service.data.ResponseOrponingList;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrponingService {

    public OrponingService(PropertyService propertyService, IRepositoryOrpon repository) {
        _propertyService = propertyService;
        _repository = repository;
    }

    private final PropertyService _propertyService;
    private final IRepositoryOrpon _repository;

    public AddressInfo OrponingAddress(EntityAddress entityAddress) throws RepositoryException {
        return _repository.GetInfo(entityAddress);
    }

    public ResponseOrponingList OrponingAddressList(List<EntityAddress> entityAddressList) {
        List<AddressInfo> addressInfo = new ArrayList<>();
        List<EntityAddress> tempAddressError = new ArrayList<>();
        List<EntityAddressError> entityAddressError = new ArrayList<>();

        for (List<EntityAddress> list : Lists.partition(entityAddressList, _propertyService.PartitionSizePars)) {
            try {
                addressInfo.addAll(_repository.GetInfo(list));
            } catch (RepositoryException re) {
                tempAddressError.addAll(list);
            }
        }

        for (EntityAddress address : tempAddressError) {
            try {
                addressInfo.add(_repository.GetInfo(address));
            } catch (RepositoryException re) {

                entityAddressError.add(new EntityAddressError(address, re.getMessage()));
            }
        }

        return new ResponseOrponingList(addressInfo, entityAddressError);
    }
}