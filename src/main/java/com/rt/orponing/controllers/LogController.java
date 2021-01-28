package com.rt.orponing.controllers;

import com.rt.orponing.service.LogReadService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogController {

    public LogController(LogReadService service) {
        _service = service;
    }

    private final LogReadService _service;

    @PostMapping(path="/log", consumes = "application/json", produces = "application/json")
    public String getAllLog() {
        return _service.readLog();
    }
}