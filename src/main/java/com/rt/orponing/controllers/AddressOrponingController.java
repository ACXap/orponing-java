// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.controllers;

import com.rt.orponing.repository.data.AddressInfo;
import com.rt.orponing.repository.data.EntityAddress;
import com.rt.orponing.service.OrponingApiService;
import com.rt.orponing.service.data.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@Lazy
@RequestMapping("/api/get_global_id")
@RequiredArgsConstructor
public class AddressOrponingController {
    private final OrponingApiService service;

    @GetMapping("")
    public CompletableFuture<AddressInfo> apiGetGlobalId(@RequestParam("address") String address) {

        return CompletableFuture.supplyAsync(() -> service.orponingAddress(new EntityAddress(1, address)));
    }

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public String apiAddTask(@RequestBody List<EntityAddress> list) {
        try {
            return service.addTask(list);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @GetMapping(path = "/status")
    public Status apiGetStatusTask(@RequestParam("id") String id) {
        try {
            return service.getStatusTask(id);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @GetMapping(path = "/result")
    public List<AddressInfo> apiGetResultTask(@RequestParam("id") String id) {
        try {
            return service.getResultTask(id);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }
}