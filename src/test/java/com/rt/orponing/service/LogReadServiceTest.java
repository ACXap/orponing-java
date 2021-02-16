package com.rt.orponing.service;

import com.rt.orponing.service.config.LogReadServiceConfig;
import com.rt.orponing.service.config.ServiceTestConfig;
import com.rt.orponing.service.config.ServiceTestStopConfig;
import com.rt.orponing.service.data.Status;
import com.rt.orponing.service.data.StatusType;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Stack;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = {LogReadServiceConfig.class, ServiceTestStopConfig.class})
class LogReadServiceTest {

    @Autowired
    private LogReadService service;

    @Test
    void getDayLog() {
        List<String> dayLog = service.getDayLog();
       // assertEquals(2, dayLog.size());
    }

    //@Test
    void clearArchive() throws ExecutionException, InterruptedException {
        CompletableFuture<Status> no = CompletableFuture.supplyAsync(()->service.clearArchive());

        assertEquals(StatusType.COMPLETED, no.get().getStatus());
    }

    @Test
    void readLog() {
        String s = service.readLog("2021-02-10");

        assertTrue(s.contains("2021-02-10 16:10:39,938"));
    }
}