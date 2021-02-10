package com.rt.orponing.dao;

import com.rt.orponing.dao.data.AddressGid;
import com.rt.orponing.dao.data.DaoException;

import java.util.List;

public interface IDbAddress {
    String getAddress(Long globalId) throws DaoException;
    List<AddressGid> getAddress(List<Long> collectionAddress) throws DaoException;
}