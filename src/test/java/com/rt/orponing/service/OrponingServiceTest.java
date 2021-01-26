package com.rt.orponing.service;

import com.rt.orponing.repository.RepositoryOrponSoap;
import com.rt.orponing.repository.data.AddressInfo;
import com.rt.orponing.repository.data.EntityAddress;
import com.rt.orponing.repository.data.EntityAddressError;
import com.rt.orponing.repository.data.RepositoryException;
import com.rt.orponing.service.data.ResponseOrponingList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrponingServiceTest {

    private OrponingService _service;

    @BeforeEach
    public void start() throws Exception {
        PropertyService propertyService = new PropertyService();
        RepositoryOrponSoap repository = new RepositoryOrponSoap(propertyService);
        _service = new OrponingService(propertyService, repository);
    }

    @Test
    void orponingAddress() throws RepositoryException {
        long globalId = 29182486;
        int id = 1;
        String level = "FIAS_HOUSE";
        String unparsed = "";

        EntityAddress entityAddress = new EntityAddress(id, "Новосибирск г., Орджоникидзе ул., дом 18");

        AddressInfo addressInfo = _service.OrponingAddress(entityAddress);

        assertEquals(id, addressInfo.Id);
        assertEquals(globalId, addressInfo.GlobalId);
        assertEquals(level, addressInfo.ParsingLevelCode);
        assertEquals(unparsed, addressInfo.UnparsedParts);
    }

    @Test
    void orponingAddressList() {
        long globalId_1 = 29182486;
        int id_1 = 1;
        String level_1 = "FIAS_HOUSE";
        String unparsed_1 = "";

        EntityAddress entityAddress_1 = new EntityAddress(id_1, "Новосибирск г., Орджоникидзе ул., дом 18");

        long globalId_2 = 3963807;
        int id_2 = 2;
        String level_2 = "FIAS_HOUSE";
        String unparsed_2 = "АТС";

        EntityAddress entityAddress_2 = new EntityAddress(id_2, "АТС Новосибирск  Орджоникидзе 19");
        List<EntityAddress> list = new ArrayList<>();
        list.add(entityAddress_1);
        list.add(entityAddress_2);

        ResponseOrponingList response  = _service.OrponingAddressList(list);
        AddressInfo addressInfo_1 = response.AddressInfoList.stream().filter(a -> a.Id == 1).findAny().orElse(null);
        AddressInfo addressInfo_2 = response.AddressInfoList.stream().filter(a -> a.Id == 2).findAny().orElse(null);

        assertEquals(id_1, addressInfo_1.Id);
        assertEquals(globalId_1, addressInfo_1.GlobalId);
        assertEquals(level_1, addressInfo_1.ParsingLevelCode);
        assertEquals(unparsed_1, addressInfo_1.UnparsedParts);

        assertEquals(id_2, addressInfo_2.Id);
        assertEquals(globalId_2, addressInfo_2.GlobalId);
        assertEquals(level_2, addressInfo_2.ParsingLevelCode);
        assertEquals(unparsed_2, addressInfo_2.UnparsedParts);

        assertFalse(response.HasError);
    }

    @Test
    void orponingAddressList_ErrorAddress() {
        long globalId_1 = 29182486;
        int id_1 = 1;
        String level_1 = "FIAS_HOUSE";
        String unparsed_1 = "";

        EntityAddress entityAddress_1 = new EntityAddress(id_1, "Новосибирск г., Орджоникидзе ул., дом 18");

        int id_2 = 2;

        EntityAddress entityAddress_2 = new EntityAddress(id_2, "165150, Архангельская обл, Вельский р-н, Вельск г., Гайдара ул., дом 14");
        List<EntityAddress> list = new ArrayList<>();
        list.add(entityAddress_1);
        list.add(entityAddress_2);

        ResponseOrponingList response  = _service.OrponingAddressList(list);
        AddressInfo addressInfo_1 = response.AddressInfoList.stream().filter(a -> a.Id == 1).findAny().orElse(null);

        EntityAddressError addressInfo_2 = response.AddressInfoError.stream().filter(a -> a.Address.Id == 2).findAny().orElse(null);

        assertEquals(id_1, addressInfo_1.Id);
        assertEquals(globalId_1, addressInfo_1.GlobalId);
        assertEquals(level_1, addressInfo_1.ParsingLevelCode);
        assertEquals(unparsed_1, addressInfo_1.UnparsedParts);

        assertEquals(id_2, addressInfo_2.Address.Id);
        assertEquals("Client received SOAP Fault from server: WsSearchAddrElByFullNamePortTypeImpl ошибка: #PON_0051 Please see the server log to find more detail regarding exact cause of the failure.", addressInfo_2.Error);

        assertTrue(response.HasError);
    }
}