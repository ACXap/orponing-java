// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.controllers;

import com.rt.orponing.service.interfaces.IStartable;
import com.rt.orponing.service.ManagerService;
import com.rt.orponing.service.data.*;
import com.rt.orponing.service.statuservices.*;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@Lazy
@AllArgsConstructor
@RequestMapping("/orponing_service/")
public class OrponingServiceController {
    private final ManagerService managerService;

    @GetMapping("{id}/start")
    public Status startService(@PathVariable String id) {
        IStartable service = managerService.getStartableService(id);
        service.start();

        return managerService.getStatus(id);
    }

    @GetMapping("status")
    public CompletableFuture<Status> updateStatusService(@RequestParam("service") String service) {

        return CompletableFuture.supplyAsync(() -> {
            AbstractStatusService s = managerService.getStatusService(service);
            return s.getStatus();
        });
    }

    @GetMapping("all_services")
    public List<InfoService> allService() {
        return managerService.getAllInfoService();
    }
}