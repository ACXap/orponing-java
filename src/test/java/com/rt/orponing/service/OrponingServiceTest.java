package com.rt.orponing.service;

import com.rt.orponing.dao.data.AddressGid;
import com.rt.orponing.dao.data.DaoException;
import com.rt.orponing.dao.interfaces.IDbAddress;
import com.rt.orponing.repository.IRepositoryOrpon;
import com.rt.orponing.repository.data.AddressInfo;
import com.rt.orponing.repository.data.EntityAddress;
import com.rt.orponing.repository.data.RepositoryException;
import com.rt.orponing.service.config.ServiceTestConfig;
import com.rt.orponing.service.config.ServiceTestStopConfig;
import com.rt.orponing.service.data.Status;
import com.rt.orponing.service.data.StatusType;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ServiceTestConfig.class, ServiceTestStopConfig.class})
@ActiveProfiles("test")
class OrponingServiceTest {

    @MockBean
    private IDbAddress db;
    @MockBean
    private IRepositoryOrpon soap;
    @Autowired
    private OrponingService service;

    @Test
    void orponingAddress() throws RepositoryException {

        long globalId = 29182486;
        int id = 1;
        String level = "FIAS_HOUSE";
        String unparsed = "";
        String checkStatus = "VALIDATED";
        String qualityCode = "UNDEF_05";

        EntityAddress entityAddress = new EntityAddress(id, "Новосибирск г., Орджоникидзе ул., дом 18");

        Mockito.doReturn(new AddressInfo(id, globalId, level, unparsed, qualityCode, checkStatus))
                .when(soap).GetInfo(entityAddress);

        AddressInfo addressInfo = service.orponingAddress(entityAddress);

        assertEquals(id, addressInfo.Id);
        assertEquals(globalId, addressInfo.GlobalId);
        assertEquals(level, addressInfo.ParsingLevelCode);
        assertEquals(unparsed, addressInfo.UnparsedParts);
        assertEquals(checkStatus, addressInfo.CheckStatus);
        assertEquals(qualityCode, addressInfo.QualityCode);
    }

    @Test
    void orponingAddressList() throws RepositoryException {
        long globalId_1 = 29182486;
        int id_1 = 1;
        String level_1 = "FIAS_HOUSE";
        String unparsed_1 = "";

        long globalId_2 = 3963807;
        int id_2 = 2;
        String level_2 = "FIAS_HOUSE";
        String unparsed_2 = "АТС";

        List<EntityAddress> list = Arrays.asList(
                new EntityAddress(id_1, "Новосибирск г., Орджоникидзе ул., дом 18"),
                new EntityAddress(id_2, "АТС Новосибирск  Орджоникидзе 19"));

        List<AddressInfo> ai = Arrays.asList(
                new AddressInfo(id_1, globalId_1, level_1, unparsed_1, "UNDEF_05", "VALIDATED"),
                new AddressInfo(id_2, globalId_2, level_2, unparsed_2, "UNDEF_05", "VALIDATED"));

        Mockito.when(soap.GetInfo(list))
                .thenReturn(ai);

        List<AddressInfo> response = service.orponingAddressList(list);
        AddressInfo addressInfo_1 = response.stream().filter(a -> a.Id == 1).findAny().get();
        AddressInfo addressInfo_2 = response.stream().filter(a -> a.Id == 2).findAny().get();

        verify(soap, times(1)).GetInfo(list);
        verify(soap, times(0)).GetInfo(ArgumentMatchers.any(EntityAddress.class));

        assertEquals(id_1, addressInfo_1.Id);
        assertEquals(globalId_1, addressInfo_1.GlobalId);
        assertEquals(level_1, addressInfo_1.ParsingLevelCode);
        assertEquals(unparsed_1, addressInfo_1.UnparsedParts);

        assertEquals(id_2, addressInfo_2.Id);
        assertEquals(globalId_2, addressInfo_2.GlobalId);
        assertEquals(level_2, addressInfo_2.ParsingLevelCode);
        assertEquals(unparsed_2, addressInfo_2.UnparsedParts);
    }

    @Test
    void setAddressById_OneAddress() throws Exception {
        long globalId = 29182486;
        String addressOrpon = "630099, Новосибирская обл, Новосибирск г., Орджоникидзе ул., дом 18";

        Mockito.when(db.getAddress(globalId))
                .thenReturn(addressOrpon);

        AddressInfo address = new AddressInfo(1, 29182486, "", "", "", "");
        service.setAddressById(address);
        assertEquals(addressOrpon, address.AddressOrpon);
    }

    @Test
    void setAddressById_ListAddress() throws DaoException {
        String addressOrpon1 = "630099, Новосибирская обл, Новосибирск г., Орджоникидзе ул., дом 18";
        String addressOrpon2 = "Новосибирская обл, Новосибирск г., Орджоникидзе ул.";

        long gid1 = 29182486L;
        long gid2 = 5916420L;

        List<AddressInfo> list = Arrays.asList(
                new AddressInfo(29182486, gid1, "", "", "", ""),
                new AddressInfo(5916420, gid2, "", "", "", ""));

        List<Long> ids = Arrays.asList(gid1, gid2);

        List<AddressGid> ag = Arrays.asList(
                new AddressGid(gid1, addressOrpon1),
                new AddressGid(gid2, addressOrpon2));

        Mockito.when(db.getAddress(ids))
                .thenReturn(ag);

        service.setAddressById(list);

        assertEquals(addressOrpon1, list.stream().filter(a -> a.GlobalId == gid1).findFirst().get().AddressOrpon);
        assertEquals(addressOrpon2, list.stream().filter(a -> a.GlobalId == gid2).findFirst().get().AddressOrpon);
    }

    @Test
    void getErrorAddress() throws RepositoryException {
        EntityAddress address = new EntityAddress(1, "error");

        Mockito.doThrow(new RepositoryException("Test error"))
                .when(soap).GetInfo(address);

        AddressInfo adr = service.orponingAddress(address);

        assertEquals(1, adr.Id);
        assertEquals("Test error", adr.Error);
        assertFalse(adr.IsValid);
    }

    @Test
    void getErrorListAddress() throws RepositoryException {
        List<EntityAddress> address = Arrays.asList(
                new EntityAddress(1, "error"),
                new EntityAddress(1, "error"));

       Mockito.doThrow(new RepositoryException("Test error"))
               .when(soap).GetInfo(address);

        Mockito.doThrow(new RepositoryException("Test error"))
                .when(soap).GetInfo(ArgumentMatchers.any(EntityAddress.class));

        List<AddressInfo> adr = service.orponingAddressList(address);

        verify(soap, times(1)).GetInfo(address);
        verify(soap, times(2)).GetInfo(ArgumentMatchers.any(EntityAddress.class));

        assertEquals(2, adr.size());
        assertEquals("Test error", adr.get(0).Error);
        assertFalse(adr.get(0).IsValid);
    }

    @Test
    void getStatusGood() throws RepositoryException {
        Mockito.doReturn(new AddressInfo(1, 29182486, "", "", "", ""))
                .when(soap).GetInfo((EntityAddress) ArgumentMatchers.any());

        Status status = service.getStatus();

        assertEquals(StatusType.START, status.getStatus());
    }

    @Test
    void getStatusError() throws RepositoryException {
        Mockito.doThrow(new RepositoryException("Test error"))
                .when(soap).GetInfo((EntityAddress) ArgumentMatchers.any());

        Status status = service.getStatus();

        assertEquals(StatusType.ERROR, status.getStatus());
        assertEquals("Ой, я упал. Поднимите меня Тестовые данные не совпадают. Сервис работает некорректно. Test error", status.getMessage());
    }

    @Test
    void orponingAddressList_NotValidAddress() {
        List<EntityAddress> entityAddress = Arrays.asList(
                new EntityAddress(1, ""),
                new EntityAddress(1, null),
                new EntityAddress(1, "   "));

        List<AddressInfo> addressInfo = service.orponingAddressList(entityAddress);

        Mockito.verifyNoInteractions(soap);
        assertEquals(3, addressInfo.size());
        assertEquals("Address is empty", addressInfo.get(0).Error);
    }
}