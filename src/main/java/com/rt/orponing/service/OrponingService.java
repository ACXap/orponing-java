// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service;

import com.google.common.collect.Lists;
import com.rt.orponing.dao.IDbAddress;
import com.rt.orponing.dao.data.AddressGid;
import com.rt.orponing.dao.data.DaoException;
import com.rt.orponing.repository.IRepositoryOrpon;
import com.rt.orponing.repository.data.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Lazy
public class OrponingService {


    public OrponingService(IRepositoryOrpon repository, IDbAddress db) {
        this.repository = repository;
        this.db = db;
    }

    private final IRepositoryOrpon repository;
    private final IDbAddress db;
    private final Logger _logger = LoggerFactory.getLogger("OrponingService");

    @Value("${soap.partition.size}")
    private int _partitionSizePars;

    public AddressInfo OrponingAddress(EntityAddress entityAddress) {
        AddressInfo addressInfo = getAddressInfo(entityAddress);

        if (!addressInfo.IsValid && addressInfo.GlobalId < 1) return addressInfo;

        try {
            addressInfo.AddressOrpon = db.getAddress(addressInfo.GlobalId);
            return addressInfo;
        } catch (Exception ex) {
            _logger.error(entityAddress.Address + " " + ex.getMessage());
            return new AddressInfo(entityAddress.Id, ex.getMessage());
        }
    }

    public List<AddressInfo> OrponingAddressList(List<EntityAddress> entityAddressList) {
        List<AddressInfo> addressInfo = new ArrayList<>();
        List<EntityAddress> tempAddressError = new ArrayList<>();

        for (List<EntityAddress> list : Lists.partition(entityAddressList, _partitionSizePars)) {
            try {
                addressInfo.addAll(repository.GetInfo(list));
            } catch (RepositoryException re) {
                tempAddressError.addAll(list);
            }
        }

        if (!tempAddressError.isEmpty()) {
            _logger.info("Orponing list bad address");

            for (EntityAddress address : tempAddressError) {
                addressInfo.add(getAddressInfo(address));
            }
        }

        return addressInfo;
    }

    public void setAddressById(List<AddressInfo> collectionAddressInfo)  {
      _logger.info("Get address by global id");

       try{
           List<AddressGid> address = db.getAddress(collectionAddressInfo.stream().filter(a->a.IsValid).map(a->a.GlobalId).collect(Collectors.toList()));

           address.parallelStream().forEach(a->{
               collectionAddressInfo.stream().filter(c->c.GlobalId == a.GlobalId).forEach(x->x.AddressOrpon = a.Address);
           });

       } catch (Exception ex){
           _logger.error(ex.getMessage());
       }
    }

    private AddressInfo getAddressInfo(EntityAddress entityAddress) {
        try {
            _logger.info("Orponing address");
            return repository.GetInfo(entityAddress);
        } catch (Exception ex) {
            _logger.error(entityAddress.Address + " " + ex.getMessage());
            return new AddressInfo(entityAddress.Id, ex.getMessage());
        }
    }
}