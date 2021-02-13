package com.rt.orponing.controllers;

import com.rt.orponing.repository.data.AddressInfo;
import com.rt.orponing.repository.data.EntityAddress;
import com.rt.orponing.service.OrponingApiService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.hamcrest.Matchers.containsString;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AddressOrponingController.class)
class AddressOrponingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    OrponingApiService service;

    @Test
    void apiGetGlobalId() throws Exception {

        AddressInfo adr = new AddressInfo(1, 29182486, "", "", "", "");
        adr.AddressOrpon = "630099, Новосибирская обл, Новосибирск г., Орджоникидзе ул., дом 18";

        Mockito.when(service.orponingAddress(ArgumentMatchers.any(EntityAddress.class))).thenReturn(adr);

        MvcResult mvcResult = mockMvc.perform(get("/api/get_global_id").param("address", "Новосибирск г., Орджоникидзе ул., дом 18"))
                .andExpect(request().asyncStarted())
                .andDo(MockMvcResultHandlers.log())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(content().json("{'GlobalId':29182486}"))
                .andExpect(content().json("{'Id':1}"))
                .andExpect(content().json("{'AddressOrpon':'630099, Новосибирская обл, Новосибирск г., Орджоникидзе ул., дом 18'}"));
    }
}