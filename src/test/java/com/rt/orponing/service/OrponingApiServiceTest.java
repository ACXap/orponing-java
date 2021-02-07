package com.rt.orponing.service;

import com.rt.orponing.repository.data.AddressInfo;
import com.rt.orponing.service.data.Status;
import com.rt.orponing.service.data.StatusType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.RejectedExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrponingApiServiceTest {

    @Autowired
    private OrponingApiService _service;

    @Test
    void addTask() {
        String taskId =_service.addTask(new ArrayList<>());

        assertNotEquals(null, taskId);
    }

    @Test
    void getResultTask_goodId()throws Exception {
        String taskId =_service.addTask(new ArrayList<>());
        List<AddressInfo> list = null;

        try{
            list = _service.getResultTask(taskId);
            fail();
        } catch (Exception ex){
            assertEquals("Task not completed", ex.getMessage());
        }

        if(list==null){
            Thread.sleep(2000);
        }
        list = _service.getResultTask(taskId);

        assertNotEquals(null, list);
    }

    @Test
    void getResultTask_errorId() throws Exception {
        try{
            List<AddressInfo> list = _service.getResultTask("12312313");
            fail();
        } catch (Exception ex){
            assertEquals("Id not correct", ex.getMessage());
        }
    }

    @Test
    void getResultTask_notFoundId() throws Exception {
        UUID uuid = UUID.randomUUID();

        try{
            List<AddressInfo> list = _service.getResultTask(uuid.toString());
            fail();
        } catch (Exception ex){
            assertEquals("Id not found", ex.getMessage());
        }
    }

    @Test
    void getStatusTask() throws Exception {
        String taskId =_service.addTask(new ArrayList<>());
        Status status = _service.getStatusTask(taskId);

        assertEquals(StatusType.STOP, status.getStatus());
    }

    @Test
    void getStatusTask_waitResult() throws Exception {
        String taskId =_service.addTask(new ArrayList<>());

        Thread.sleep(2000);
        Status status = _service.getStatusTask(taskId);
        assertEquals(StatusType.COMPLETED, status.getStatus());
    }

    @Test
    void testManyRequest()  {

        try{
            for(int i = 0; i<25;i++){
                _service.addTask(new ArrayList<>());
            }

            fail();
        } catch (Exception ex){
            assertTrue(ex instanceof RejectedExecutionException);
        }
    }

    @Test
    void testManyRequest_enoughThread() {

        try{
            for(int i = 0; i<14;i++){
                _service.addTask(new ArrayList<>());
            }
        } catch (Exception ex){
            fail();
        }
    }
}