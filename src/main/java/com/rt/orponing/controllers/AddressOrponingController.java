// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.controllers;

import com.rt.orponing.repository.data.AddressInfo;
import com.rt.orponing.repository.data.EntityAddress;
import com.rt.orponing.service.OrponingService;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Lazy
public class AddressOrponingController {

    public AddressOrponingController(OrponingService service) {
        _service = service;
    }

    private final OrponingService _service;

    @GetMapping("/get_globalid")
    public AddressInfo getGlobalIdPage(@RequestParam("address") String address) {
        return _service.OrponingAddress(new EntityAddress(1, address));
    }

    @PostMapping(path = "/get_globalid", consumes = "application/json", produces = "application/json")
    public List<AddressInfo> getGlobalIdsPage(@RequestBody List<EntityAddress> list) {
        return _service.OrponingAddressList(list);
    }
}