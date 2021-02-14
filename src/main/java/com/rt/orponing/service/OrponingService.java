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
import com.sun.istack.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
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

    public AddressInfo orponingAddress(EntityAddress entityAddress) {
        if (!isValidAddress(entityAddress)) return new AddressInfo(entityAddress.Id, "Address is empty");
        logger.info("Orponing address: " + entityAddress.Address);

        try {
            return repository.GetInfo(entityAddress);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return new AddressInfo(entityAddress.Id, ex.getMessage());
        }
    }

    public List<AddressInfo> orponingAddressList(@NonNull List<EntityAddress> entityAddressList) {
        if (entityAddressList.isEmpty()) return Collections.emptyList();

        List<EntityAddress> tempAddressError = new ArrayList<>();

        logger.info("Orponing list address with valid address");
        List<AddressInfo> addressInfo = new ArrayList<>(orponingListAddressValidAddress(entityAddressList, tempAddressError));

        logger.info("Orponing list address with error");
        addressInfo.addAll(orponingListAddressOneByOne(tempAddressError));

        logger.info("Orponing list address with empty address");
        addressInfo.addAll(orponingListAddressWithEmptyAddress(entityAddressList));

        return addressInfo;
    }

    public void setAddressById(@NonNull List<AddressInfo> collectionAddressInfo) {
        logger.info("Get address by gid");
        if (collectionAddressInfo.isEmpty()) return;

        try {
            List<AddressGid> address = db.getAddress(collectionAddressInfo.stream().filter(a -> a.IsValid).map(a -> a.GlobalId).distinct().collect(Collectors.toList()));
            address.parallelStream().forEach(a -> collectionAddressInfo.stream().filter(c -> c.GlobalId == a.GlobalId).forEach(x -> x.AddressOrpon = a.Address));
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }
    
    public void setAddressById(@NotNull AddressInfo addressInfo){
        logger.info("Get address by gid");
        if(!addressInfo.IsValid) return;

        try{
            addressInfo.AddressOrpon = db.getAddress(addressInfo.GlobalId);
        } catch (Exception ex){
            logger.error(ex.getMessage());
            addressInfo.Error = ex.getMessage();
        }
    }

    @Override
    public Status getStatus() {
        String testAddress = "Новосибирск г., Орджоникидзе ул., дом 18";
        long testGlobalId = 29182486;

        AddressInfo addressInfo = orponingAddress(new EntityAddress(1, testAddress));

        return addressInfo.GlobalId == testGlobalId ? Status.Start(StatusMessage.START) : Status.Error(StatusMessage.ERROR + " Тестовые данные не совпадают. Сервис работает некорректно. " + addressInfo.Error);
    }

    //endregion PublicMethod

    //region PrivateMethod
    private List<AddressInfo> orponingListAddressWithEmptyAddress(@NonNull List<EntityAddress> entityAddressList) {
        if (entityAddressList.isEmpty()) return Collections.emptyList();

        List<AddressInfo> addressEmpty = entityAddressList
                .stream()
                .filter(a -> !isValidAddress(a))
                .map(a -> new AddressInfo(a.Id, "Address is empty"))
                .collect(Collectors.toList());

        if (addressEmpty.isEmpty()) return Collections.emptyList();

        return addressEmpty;
    }

    private List<AddressInfo> orponingListAddressOneByOne(@NonNull List<EntityAddress> entityAddressList) {
        if (entityAddressList.isEmpty()) return Collections.emptyList();

        List<AddressInfo> addressInfo = new ArrayList<>();

        for (EntityAddress address : entityAddressList) {
            addressInfo.add(orponingAddress(address));
        }

        return addressInfo;
    }

    private List<AddressInfo> orponingListAddressValidAddress(@NotNull List<EntityAddress> entityAddressList, @NotNull List<EntityAddress> tempAddressError) {
        if(entityAddressList.isEmpty()) return Collections.emptyList();

        List<AddressInfo> addressInfo = new ArrayList<>();

        for (List<EntityAddress> list : Lists.partition(entityAddressList.stream().filter(this::isValidAddress).collect(Collectors.toList()), partitionSizePars)) {
            try {
                addressInfo.addAll(repository.GetInfo(list));
            } catch (RepositoryException re) {
                tempAddressError.addAll(list);
            }
        }

        return  addressInfo;
    }

    private boolean isValidAddress(EntityAddress entityAddress) {
        return entityAddress != null && entityAddress.Address != null && !entityAddress.Address.trim().isEmpty();
    }
    //endregion PrivateMethod
}