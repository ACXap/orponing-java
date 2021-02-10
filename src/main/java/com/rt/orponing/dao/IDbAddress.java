package com.rt.orponing.dao;

import com.rt.orponing.dao.data.DaoException;

public interface IDbAddress {
    String getAddress(Long globalId) throws DaoException;
}