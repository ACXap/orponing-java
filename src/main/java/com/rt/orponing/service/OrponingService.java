package com.rt.orponing.service;

import com.google.common.collect.Lists;
import com.rt.orponing.repository.IRepositoryOrpon;
import com.rt.orponing.repository.data.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger _logger = LoggerFactory.getLogger("OrponingService");

    public AddressInfo OrponingAddress(EntityAddress entityAddress) {
        try {
            _logger.info("Orponing address");
            AddressInfo addressInfo = _repository.GetInfo(entityAddress);

            // тут еще надо получить строку адреса из орпон

            return addressInfo;
        } catch (Exception ex) {

            _logger.error(entityAddress.Address + " " +  ex.getMessage());
            return new AddressInfo(entityAddress.Id, ex.getMessage());
        }
    }

    public List<AddressInfo> OrponingAddressList(List<EntityAddress> entityAddressList) {
        List<AddressInfo> addressInfo = new ArrayList<>();
        List<EntityAddress> tempAddressError = new ArrayList<>();

        _logger.info("Orponing list address");
        for (List<EntityAddress> list : Lists.partition(entityAddressList, _propertyService.PartitionSizePars)) {
            try {
                addressInfo.addAll(_repository.GetInfo(list));
            } catch (RepositoryException re) {
                tempAddressError.addAll(list);
            }
        }

        _logger.info("Orponing list bad address");
        for (EntityAddress address : tempAddressError) {
            addressInfo.add(OrponingAddress(address));
        }

        return addressInfo;
    }
}