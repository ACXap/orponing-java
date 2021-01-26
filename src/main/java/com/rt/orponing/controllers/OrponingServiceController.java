package com.rt.orponing.controllers;

import com.rt.orponing.dao.data.DaoException;
import com.rt.orponing.service.OrponingTableService;
import com.rt.orponing.service.data.StatusService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrponingServiceController {

    public OrponingServiceController(OrponingTableService service) {
        _service = service;
    }

    private final OrponingTableService _service;

    @GetMapping("/orponing_service/start")
    public StatusService startService() throws DaoException {
        StatusService statusService = _service.startService();

        return statusService;
    }

    @GetMapping("/orponing_service/status")
    public StatusService statusService() throws DaoException {
        StatusService statusService = _service.getStatusService();

        return statusService;
    }
}