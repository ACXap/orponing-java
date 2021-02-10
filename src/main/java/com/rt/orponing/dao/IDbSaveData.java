// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.dao;

import com.rt.orponing.dao.data.DaoException;
import com.rt.orponing.repository.data.*;

import java.util.List;

public interface IDbSaveData {
    List<EntityAddress> GetEntityAddress() throws DaoException;

    void UpdateEntityAddress(List<AddressInfo> collectionAddressInfo) throws DaoException;
}