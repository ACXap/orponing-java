// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service;

import com.google.common.collect.Lists;
import com.rt.orponing.dao.interfaces.IDbAddress;
import com.rt.orponing.dao.data.AddressGid;
import com.rt.orponing.repository.IRepositoryOrpon;
import com.rt.orponing.repository.data.*;
import com.rt.orponing.service.data.Status;
import com.rt.orponing.service.data.StatusMessage;
import com.rt.orponing.service.interfaces.IStatus;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Lazy
@RequiredArgsConstructor
public class OrponingService implements IStatus {

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
            logger.info("Orponing address: " + entityAddress.Address);
            return repository.GetInfo(entityAddress);
        } catch (Exception ex) {
            logger.error(entityAddress.Address + " " + ex.getMessage());
            return new AddressInfo(entityAddress.Id, ex.getMessage());
        }
    }

    public List<AddressInfo> OrponingAddressList(List<EntityAddress> entityAddressList) {
        logger.info("Orponing collection address");

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
        logger.info("Get address by gid");

        try {
            List<AddressGid> address = db.getAddress(collectionAddressInfo.stream().filter(a -> a.IsValid).map(a -> a.GlobalId).distinct().collect(Collectors.toList()));
            address.parallelStream().forEach(a -> collectionAddressInfo.stream().filter(c -> c.GlobalId == a.GlobalId).forEach(x -> x.AddressOrpon = a.Address));
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    public String getAddressById(Long globalId) throws Exception {
        return db.getAddress(globalId);
    }

    @Override
    public Status getStatus() {
        String testAddress = "Новосибирск г., Орджоникидзе ул., дом 18";
        long testGlobalId = 29182486;

        AddressInfo addressInfo = OrponingAddress(new EntityAddress(1, testAddress));

        return addressInfo.GlobalId == testGlobalId ? Status.Start(StatusMessage.START) : Status.Error(StatusMessage.ERROR + " Тестовые данные не совпадают. Сервис работает некорректно. " + addressInfo.Error);
    }

    //endregion PublicMethod
}