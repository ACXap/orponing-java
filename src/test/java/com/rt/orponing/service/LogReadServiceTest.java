package com.rt.orponing.service;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LogReadServiceTest {

    LogReadService service = new LogReadService();

    @Test
    void getDayLog() {
        List<String> dayLog = service.getDayLog();
        assertEquals(2, dayLog.size());
    }

    //@Test
    void clearArchive() {
        service.clearArchive();
    }

    @Test
    void readLog() {
        String s = service.readLog("2021-02-08");

        assertTrue(s.contains("2021-02-08 11:17:42,796"));
    }
}