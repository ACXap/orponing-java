package com.rt.orponing.controllers;

import com.rt.orponing.repository.IRepositoryOrpon;
import com.rt.orponing.repository.data.AddressInfo;
import com.rt.orponing.repository.data.EntityAddress;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressOrponingController {

    public  AddressOrponingController(IRepositoryOrpon repository){
        _repository = repository;
    }

    private final IRepositoryOrpon _repository;

    @GetMapping("/get_globalid")
    public AddressInfo getMainPage(@RequestParam("address") String address){

        try {
            return _repository.GetInfo(new EntityAddress(1, address));
        }  catch (Exception ex){
            return AddressInfo.GetErrorAddressInfo();
        }
    }
}