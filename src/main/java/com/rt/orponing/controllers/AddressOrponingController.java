// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.controllers;

import com.rt.orponing.repository.data.AddressInfo;
import com.rt.orponing.repository.data.EntityAddress;
import com.rt.orponing.service.OrponingApiService;
import com.rt.orponing.service.OrponingService;
import com.rt.orponing.service.data.Status;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@Lazy
public class AddressOrponingController {

    public AddressOrponingController(OrponingService service, OrponingApiService oas) {
        _service = service;
        _oas = oas;
    }

    private final OrponingService _service;
    private final OrponingApiService _oas;

    @GetMapping("/get_globalid")
    public AddressInfo getGlobalIdPage(@RequestParam("address") String address) {
        return _service.OrponingAddress(new EntityAddress(1, address));
    }

    @PostMapping(path = "/api/get_global_id", consumes = "application/json", produces = "application/json")
    public String apiAddTask(@RequestBody List<EntityAddress> list) {
        try {
            return _oas.addTask(list);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @GetMapping(path = "/api/get_global_id/status")
    public Status apiGetStatusTask(@RequestParam("id") String id) throws Exception {
        try {
            return _oas.getStatusTask(id);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @GetMapping(path = "/api/get_global_id/result")
    public List<AddressInfo> getgetResultTask(@RequestParam("id") String id) throws Exception {
        try {
            return _oas.getResultTask(id);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }
}