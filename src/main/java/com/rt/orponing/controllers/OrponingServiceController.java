// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.controllers;

import com.rt.orponing.service.IStartable;
import com.rt.orponing.service.ManagerService;
import com.rt.orponing.service.NotFoundStartableService;
import com.rt.orponing.service.data.*;
import com.rt.orponing.service.statuservices.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@RestController
@Lazy
public class OrponingServiceController {

    public OrponingServiceController(ManagerService managerService) {
        this.managerService = managerService;
    }

    private final ManagerService managerService;

    @GetMapping("/orponing_service/{id}/start")
    public Status startService(@PathVariable String id) {
        IStartable service = managerService.getStartableService(id);
        service.start();

        return managerService.getStatus(id);
    }

    @GetMapping("/orponing_service/status")
    public CompletableFuture<Status> updateStatusService(@RequestParam("service") String service) {

        return CompletableFuture.supplyAsync(() -> {
            AbstractStatusService s = managerService.getStatusService(service);
            return s.getStatus();
        });
    }

    @GetMapping("/orponing_service/all_services")
    public List<InfoService> allService() {
        return managerService.getAllInfoService();
    }
}