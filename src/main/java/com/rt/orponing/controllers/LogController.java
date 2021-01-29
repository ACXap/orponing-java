// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.controllers;

import com.rt.orponing.service.LogReadService;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Lazy
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