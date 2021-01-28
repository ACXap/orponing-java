package com.rt.orponing.controllers;

import com.rt.orponing.service.OrponingTableService;
import com.rt.orponing.service.StatusReadService;
import com.rt.orponing.service.data.StatusService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrponingServiceController {

    public OrponingServiceController(OrponingTableService service, StatusReadService srs) {
        _service = service;
        _srs = srs;
    }

    private final OrponingTableService _service;
    private final StatusReadService _srs;

    @GetMapping("/orponing_service/start")
    public StatusService startService() {
        StatusService statusService = _service.startService();

        return statusService;
    }

    @GetMapping("/orponing_service/status")
    public StatusService statusService(@RequestParam("service") String service) {

        if (service.equals("db")) {
            return _srs.getStatusDbSaveData();
        } else if (service.equals("orponing")) {
            return _srs.getStatusOrponingService();
        } else if (service.equals("orponing-service")) {
            return _srs.getStatusOrponingTableService();
        }

        return null;
    }
}