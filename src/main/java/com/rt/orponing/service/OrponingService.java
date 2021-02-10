// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service;

import com.google.common.collect.Lists;
import com.rt.orponing.dao.interfaces.IDbAddress;
import com.rt.orponing.dao.data.AddressGid;
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

    //region PrivateField

    private final IRepositoryOrpon repository;
    private final IDbAddress db;
    private final Logger logger = LoggerFactory.getLogger("OrponingService");

    @Value("${soap.partition.size}")
    private int partitionSizePars;

    //endregion PrivateField

    //region PublicMethod

    public AddressInfo OrponingAddress(EntityAddress entityAddress) {
        try {
            logger.info("Orponing address");
            return repository.GetInfo(entityAddress);
        } catch (Exception ex) {
            logger.error(entityAddress.Address + " " + ex.getMessage());
            return new AddressInfo(entityAddress.Id, ex.getMessage());
        }
    }

    public List<AddressInfo> OrponingAddressList(List<EntityAddress> entityAddressList) {
        List<AddressInfo> addressInfo = new ArrayList<>();
        List<EntityAddress> tempAddressError = new ArrayList<>();

        for (List<EntityAddress> list : Lists.partition(entityAddressList, partitionSizePars)) {
            try {
                addressInfo.addAll(repository.GetInfo(list));
            } catch (RepositoryException re) {
                tempAddressError.addAll(list);
            }
        }

        if (!tempAddressError.isEmpty()) {
            logger.info("Orponing list bad address");

            for (EntityAddress address : tempAddressError) {
                addressInfo.add(OrponingAddress(address));
            }
        }

        return addressInfo;
    }

    public void setAddressById(List<AddressInfo> collectionAddressInfo) {
        logger.info("Get address by global id");

        try {
            List<AddressGid> address = db.getAddress(collectionAddressInfo.stream().filter(a -> a.IsValid).map(a -> a.GlobalId).collect(Collectors.toList()));
            address.parallelStream().forEach(a -> collectionAddressInfo.stream().filter(c -> c.GlobalId == a.GlobalId).forEach(x -> x.AddressOrpon = a.Address));
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    public String getAddressById(Long globalId) throws Exception {
        return db.getAddress(globalId);
    }

    //endregion PublicMethod
}