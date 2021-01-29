package com.rt.orponing.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogReadServiceTest {

    final LogReadService  service = new LogReadService();

    @Test
    void readLog() {
        String log = service.readLog();
        System.out.println(log);
    }
}