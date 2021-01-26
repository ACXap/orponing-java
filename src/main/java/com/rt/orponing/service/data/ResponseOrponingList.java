package com.rt.orponing.service.data;

import com.rt.orponing.repository.data.AddressInfo;
import com.rt.orponing.repository.data.EntityAddressError;

import java.util.List;

public class ResponseOrponingList {

    public ResponseOrponingList(List<AddressInfo> addressInfoList, List<EntityAddressError> addressInfoError) {
        AddressInfoList = addressInfoList;
        AddressInfoError = addressInfoError;

        HasError = !AddressInfoError.isEmpty();
    }

    public final boolean HasError;

    public final List<AddressInfo> AddressInfoList;
    public final List<EntityAddressError> AddressInfoError;
}