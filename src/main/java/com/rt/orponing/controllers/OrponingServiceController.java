package com.rt.orponing.controllers;

import com.rt.orponing.dao.DaoException;
import com.rt.orponing.service.OrponingService;
import com.rt.orponing.service.data.StatusService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrponingServiceController {

    public OrponingServiceController(OrponingService service) {
        _service = service;
    }

    private final OrponingService _service;

    @GetMapping("/orponing_service")
    public String orponingService() throws DaoException {
        String urls = "/orponing_service/start - запуск сервиса <br>" +
                "/orponing_service/status - проверка статуса сервиса";

        return urls;
    }

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