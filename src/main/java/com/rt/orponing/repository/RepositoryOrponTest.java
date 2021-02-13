package com.rt.orponing.repository;

import com.rt.orponing.repository.data.AddressInfo;
import com.rt.orponing.repository.data.EntityAddress;
import com.rt.orponing.repository.data.RepositoryException;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Lazy
@Primary
public class RepositoryOrponTest implements IRepositoryOrpon{
    @Override
    public AddressInfo GetInfo(EntityAddress entityAddress) throws RepositoryException {
        return  new AddressInfo(1,1,"","","","");
    }

    @Override
    public List<AddressInfo> GetInfo(List<EntityAddress> entityAddressList) throws RepositoryException {
        List<AddressInfo> list = new ArrayList<>();
        list.add(new AddressInfo(1,1,"","","",""));
        list.add(new AddressInfo(2,2,"","","",""));
        return list;
    }
}