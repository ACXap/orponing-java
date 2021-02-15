// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.controllers;

import com.rt.orponing.service.LogReadService;
import com.rt.orponing.service.PasswordService;
import com.rt.orponing.service.data.Status;
import com.rt.orponing.service.data.StatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@Lazy
@RequiredArgsConstructor
public class LogController {
    private final LogReadService service;
    private final PasswordService passwordService;

    @PostMapping(path = "/log", consumes = "application/json", produces = "application/json")
    public CompletableFuture<String> getAllLog() {
        return CompletableFuture.supplyAsync(service::readLogToday);
    }

    @GetMapping("/log/clear")
    public CompletableFuture<Status> clearLog(@RequestParam("password") String password) {
        return CompletableFuture.supplyAsync(() -> {
            Status status = passwordService.clearLog(password);
            if(status.getStatus() == StatusType.START) return service.clearArchive();

            return status;
        });
    }

    @GetMapping("/log/read")
    public CompletableFuture<String> readLogFile(@RequestParam("file") String file) {
        return CompletableFuture.supplyAsync(() -> service.readLog(file));
    }

    @GetMapping("/log/files")
    public CompletableFuture<List<String>> getLogFiles() {
        return CompletableFuture.supplyAsync(service::getDayLog);
    }
}