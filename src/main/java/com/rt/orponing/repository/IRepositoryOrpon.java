// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.repository;

import com.rt.orponing.repository.data.*;
import java.util.List;

public interface IRepositoryOrpon {

    AddressInfo GetInfo(EntityAddress entityAddress) throws RepositoryException;

    List<AddressInfo> GetInfo(List<EntityAddress> entityAddressList) throws RepositoryException;
}