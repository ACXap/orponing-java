// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.dao.interfaces;

import com.rt.orponing.dao.data.*;

import java.util.List;

public interface IDbAddress {
    String getAddress(Long globalId) throws DaoException;
    List<AddressGid> getAddress(List<Long> collectionAddress) throws DaoException;
}