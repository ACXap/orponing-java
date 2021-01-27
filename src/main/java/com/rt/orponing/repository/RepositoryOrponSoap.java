package com.rt.orponing.repository;

import com.rt.orponing.repository.data.*;
import com.rt.orponing.repository.soap.*;
import com.rt.orponing.service.PropertyService;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RepositoryOrponSoap implements IRepositoryOrpon {

    public RepositoryOrponSoap(PropertyService propertyService) throws RepositoryException {
        try {
            _wsSearch = new WsSearchAddressElementByFullName2(propertyService.UrlService).getWsSearchAddrElByFullNamePortTypeImpl2Port();
        } catch (Exception ex) {
            throw new RepositoryException(ex.getMessage());
        }
    }

    private final WsSearchAddrElByFullNamePortType2 _wsSearch;

    @Override
    public AddressInfo GetInfo(EntityAddress entityAddress) throws RepositoryException {
        try {
            AddressElementNameResponse2 addressElementNameResponse2 = GetAddressElementNameResponse(new ArrayList<EntityAddress>() {{
                add(entityAddress);
            }});

            return ParsAddressInfo(addressElementNameResponse2.getAddressElementResponseList2().getAddressElementNameGroup2().get(0));
        } catch (Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    @Override
    public List<AddressInfo> GetInfo(List<EntityAddress> entityAddressList) throws RepositoryException {
        try {
            AddressElementNameResponse2 addressElementNameResponse2 = GetAddressElementNameResponse(entityAddressList);

            return addressElementNameResponse2.getAddressElementResponseList2().getAddressElementNameGroup2().stream().map(this::ParsAddressInfo).collect(Collectors.toList());
        } catch (Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    private AddressElementNameResponse2 GetAddressElementNameResponse(List<EntityAddress> entityAddressList) throws FaultMessage {
        AddressElementNameData.AddressElementFullNameList addressElementFullNameList = new AddressElementNameData.AddressElementFullNameList();
        List<AddressElementNameData.AddressElementFullNameList.AddressElementFullNameGroup> addressElementFullNameGroup = addressElementFullNameList.getAddressElementFullNameGroup();

        for (EntityAddress entityAddress : entityAddressList) {
            addressElementFullNameGroup.add(ParsFullNameGroup(entityAddress));
        }

        AddressElementNameData addressElementNameData = new AddressElementNameData();
        addressElementNameData.setAddressElementFullNameList(addressElementFullNameList);

        return _wsSearch.searchAddressElementByFullName(addressElementNameData);
    }

    private AddressInfo ParsAddressInfo(AddressElementNameResponse2.AddressElementResponseList2.AddressElementNameGroup2 adr) {
        return new AddressInfo(Integer.parseInt(adr.getSystemCode()),
                ParsGlobalId(adr.getGlobalID()),
                adr.getParsingLevelCode(),
                adr.getUnparsedParts(),
                adr.getQualityCode(),
                adr.getCheckStatus());
    }

    private long ParsGlobalId(String globalId) {
        try {
            return Long.parseLong(globalId);
        } catch (Exception ex) {
            return -1;
        }
    }

    private AddressElementNameData.AddressElementFullNameList.AddressElementFullNameGroup ParsFullNameGroup(EntityAddress entityAddress) {
        AddressElementNameData.AddressElementFullNameList.AddressElementFullNameGroup addressElementFullNameGroup1 = new AddressElementNameData.AddressElementFullNameList.AddressElementFullNameGroup();
        addressElementFullNameGroup1.setFullAddress(entityAddress.Address);
        addressElementFullNameGroup1.setSystemCode(Integer.toString(entityAddress.Id));

        return addressElementFullNameGroup1;
    }
}