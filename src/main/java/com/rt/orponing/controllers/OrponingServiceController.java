// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.controllers;

import com.rt.orponing.service.OrponingTableService;
import com.rt.orponing.service.data.Status;
import com.rt.orponing.service.statuservices.StatusService;
import com.rt.orponing.service.statuservices.StatusServiceDefault;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@RestController
@Lazy
public class OrponingServiceController {

    public OrponingServiceController(OrponingTableService service, List<StatusService> listStatusServices) {
        _service = service;
        _statusServices = listStatusServices.stream().collect(toMap(StatusService::getId, s -> s));
    }

    private final OrponingTableService _service;
    private final Map<String, StatusService> _statusServices;

    @GetMapping("/orponing_service/start")
    public Status startService() {
        Status status = _service.startService();

        return status;
    }

    @GetMapping("/orponing_service/status")
    public Status updateStatusService(@RequestParam("service") String service) {
        StatusService s = _statusServices.getOrDefault(service, new StatusServiceDefault());

        return s.getStatus();
    }

    @GetMapping("/orponing_service/all_status")
    public List<StatusService> statusService( ) {
        return new ArrayList<>(_statusServices.values());
    }
}