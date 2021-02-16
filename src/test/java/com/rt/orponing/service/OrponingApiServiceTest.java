package com.rt.orponing.service;

import com.rt.orponing.repository.data.AddressInfo;
import com.rt.orponing.service.config.ServiceTestConfig;
import com.rt.orponing.service.config.ServiceTestStopConfig;
import com.rt.orponing.service.data.Status;
import com.rt.orponing.service.data.StatusType;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.List;
import java.util.UUID;
import java.util.concurrent.RejectedExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
class OrponingApiServiceTest {

    //@Autowired
    private OrponingApiService service;

    //@MockBean
    private OrponingService orponingService;

    @BeforeEach
    public void start(){
        orponingService = Mockito.mock(OrponingService.class);
        service = new OrponingApiService(orponingService);
    }

    @Test
    void addTask() {
        String taskId = service.addTask(new ArrayList<>());

        assertNotEquals(null, taskId);
    }

    @Test
    void getResultTask_goodId() throws Exception {
        String taskId = service.addTask(new ArrayList<>());
        List<AddressInfo> list = null;
Thread.sleep(1000);
      //  try {
            list = service.getResultTask(taskId);

//            fail();
//        } catch (Exception ex) {
//            assertEquals("Task not completed", ex.getMessage());
//        }

        if (list == null) {
            Thread.sleep(2000);
        }
        list = service.getResultTask(taskId);

        Mockito.verify(orponingService, times(1)).setAddressById(ArgumentMatchers.anyList());
        assertNotEquals(null, list);
    }

    @Test
    void getResultTask_errorId() throws Exception {
        try {
            List<AddressInfo> list = service.getResultTask("12312313");
            fail();
        } catch (Exception ex) {
            assertEquals("Id not correct", ex.getMessage());
        }
    }

    @Test
    void getResultTask_notFoundId() throws Exception {
        UUID uuid = UUID.randomUUID();

        try {
            List<AddressInfo> list = service.getResultTask(uuid.toString());
            fail();
        } catch (Exception ex) {
            assertEquals("Id not found", ex.getMessage());
        }
    }

    @Test
    void getStatusTask() throws Exception {
        String taskId = service.addTask(new ArrayList<>());
        Status status = service.getStatusTask(taskId);

        Thread.sleep(2000);

        assertEquals(StatusType.START, status.getStatus());
    }

    @Test
    void getStatusTask_waitResult() throws Exception {
        String taskId = service.addTask(new ArrayList<>());

        Thread.sleep(2000);
        Status status = service.getStatusTask(taskId);

        Mockito.verify(orponingService, times(1)).setAddressById(ArgumentMatchers.anyList());
        assertEquals(StatusType.COMPLETED, status.getStatus());
    }

    @Test
    void testManyRequest() {

        try {
            for (int i = 0; i < 165; i++) {
                service.addTask(new ArrayList<>());
            }
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof RejectedExecutionException);
        }
    }

    @Test
    void testManyRequest_enoughThread() {

        try {
            for (int i = 0; i < 4; i++) {
                service.addTask(new ArrayList<>());
            }
        } catch (Exception ex) {
            fail();
        }
    }
}