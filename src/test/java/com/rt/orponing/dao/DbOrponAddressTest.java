package com.rt.orponing.dao;

import com.rt.orponing.dao.data.AddressGid;
import com.rt.orponing.dao.data.DaoException;
import com.rt.orponing.dao.interfaces.IDbAddress;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("application-test")
class DbOrponAddressTest {

    @Autowired
    private IDbAddress db;

    @Test
    void getAddressHouse() throws DaoException {
        assertEquals("630099, Новосибирская обл, Новосибирск г., Орджоникидзе ул., дом 18", db.getAddress(29182486L));
    }

    @Test
    void getAddressStreet() throws DaoException {
        assertEquals("Новосибирская обл, Новосибирск г., Орджоникидзе ул.", db.getAddress(5916420L));
    }

    @Test
    void getAddress() throws DaoException {
        List<Long> ids = new ArrayList<>();
        ids.add(29182486L);
        ids.add(5916420L);

        List<AddressGid> address = db.getAddress(ids);

        assertEquals("630099, Новосибирская обл, Новосибирск г., Орджоникидзе ул., дом 18", address.stream().filter(a -> a.GlobalId == 29182486).findFirst().get().Address);
        assertEquals("Новосибирская обл, Новосибирск г., Орджоникидзе ул.", address.stream().filter(a -> a.GlobalId == 5916420).findFirst().get().Address);
    }
}