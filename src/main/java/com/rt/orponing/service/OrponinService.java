package com.rt.orponing.service;

import com.google.common.collect.Lists;
import com.rt.orponing.repository.IRepositoryOrpon;
import com.rt.orponing.repository.data.AddressInfo;
import com.rt.orponing.repository.data.EntityAddress;
import com.rt.orponing.repository.data.EntityAddressError;
import com.rt.orponing.repository.data.RepositoryException;
import com.rt.orponing.service.data.ResponseOrponingList;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrponinService {

    public OrponinService(PropertyService propertyService, IRepositoryOrpon repository) {
        _propertyService = propertyService;
        _repository = repository;
    }

    private final PropertyService _propertyService;
    private final IRepositoryOrpon _repository;

    public AddressInfo OrponingAddress(EntityAddress entityAddress) throws RepositoryException {
        AddressInfo addressInfo = _repository.GetInfo(entityAddress);

        return addressInfo;
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

        ResponseOrponingList response = new ResponseOrponingList(addressInfo, entityAddressError);
        return response;
    }
}