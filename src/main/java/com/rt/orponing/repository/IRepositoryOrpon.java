package com.rt.orponing.repository;

import com.rt.orponing.repository.data.*;
import java.util.List;

public interface IRepositoryOrpon {

    AddressInfo GetInfo(EntityAddress entityAddress) throws RepositoryException;

    List<AddressInfo> GetInfo(List<EntityAddress> entityAddressList) throws RepositoryException;
}