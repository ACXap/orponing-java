// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@Lazy
public class LogReadService {

    private static final String FILE = "logs/logger.log";

    public String readLog() {
        try {
            return new String(Files.readAllBytes(Paths.get(FILE)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}