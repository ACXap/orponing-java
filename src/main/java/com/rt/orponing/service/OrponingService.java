// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service;

import com.google.common.collect.Lists;
import com.rt.orponing.repository.IRepositoryOrpon;
import com.rt.orponing.repository.data.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Lazy
public class OrponingService {

    public OrponingService( IRepositoryOrpon repository) {
        _repository = repository;
    }

    private final IRepositoryOrpon _repository;
    private final Logger _logger = LoggerFactory.getLogger("OrponingService");
    @Value("${soap.partition.size}")
    private int _partitionSizePars;

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

        for (List<EntityAddress> list : Lists.partition(entityAddressList, _partitionSizePars)) {
            try {
                addressInfo.addAll(_repository.GetInfo(list));
            } catch (RepositoryException re) {
                tempAddressError.addAll(list);
            }
        }

        if(!tempAddressError.isEmpty()) {
            _logger.info("Orponing list bad address");

            for (EntityAddress address : tempAddressError) {
                addressInfo.add(OrponingAddress(address));
            }
        }

        return addressInfo;
    }
}