package com.rt.orponing.service;

import com.rt.orponing.repository.IRepositoryOrpon;
import com.rt.orponing.repository.data.AddressInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class OrponingServiceDbTest {
    @Autowired
    private OrponingService _service;
    @MockBean
    private IRepositoryOrpon repository;

    @Test
    void setAddressById() {
        List<AddressInfo> list = new ArrayList<>();
        list.add(new AddressInfo(29182486, 29182486, "", "", "", ""));
        list.add(new AddressInfo(5916420, 5916420, "", "", "", ""));

        _service.setAddressById(list);

        assertEquals("630099, Новосибирская обл, Новосибирск г., Орджоникидзе ул., дом 18", list.stream().filter(a -> a.GlobalId == 29182486).findFirst().get().AddressOrpon);
        assertEquals("Новосибирская обл, Новосибирск г., Орджоникидзе ул.", list.stream().filter(a -> a.GlobalId == 5916420).findFirst().get().AddressOrpon);
    }
}