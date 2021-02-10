package com.rt.orponing.dao;

import com.rt.orponing.dao.data.DaoException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

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
}