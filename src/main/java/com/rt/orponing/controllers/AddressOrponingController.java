package com.rt.orponing.controllers;

import com.rt.orponing.repository.data.AddressInfo;
import com.rt.orponing.repository.data.EntityAddress;
import com.rt.orponing.service.OrponingService;
import com.rt.orponing.service.data.ResponseOrponingList;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AddressOrponingController {

    public  AddressOrponingController(OrponingService service){
        _service = service;
    }

    private final OrponingService _service;

    @GetMapping("/get_globalid")
    public AddressInfo getGlobalIdPage(@RequestParam("address") String address){

        try {
            return _service.OrponingAddress(new EntityAddress(1, address));
        }  catch (Exception ex){
            return AddressInfo.GetErrorAddressInfo();
        }
    }

    @PostMapping(path ="/get_globalid", consumes = "application/json", produces = "application/json")
    public ResponseOrponingList getGlobalIdsPage(@RequestBody List<EntityAddress> list){

        ResponseOrponingList response = _service.OrponingAddressList(list);

        return response;
    }
}