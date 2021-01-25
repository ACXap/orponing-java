package com.rt.orponing.dao;

import com.rt.orponing.repository.data.AddressInfo;
import com.rt.orponing.repository.data.EntityAddress;

import java.util.List;

public interface IDbSaveData {
    List<EntityAddress> GetEntityAddress() throws DaoException;

    void AddAddressInfo(List<AddressInfo> collectionAddressInfo) throws DaoException;
    void AddAddressInfoError(List<EntityAddress> collectionId, String error) throws DaoException;
}