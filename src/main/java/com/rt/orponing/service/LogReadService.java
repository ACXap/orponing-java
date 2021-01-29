// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class LogReadService {

    private static final String FILE = "logs/spring-boot-logger.log";

    public String readLog() {
        String content = null;

        try {
            content = new String(Files.readAllBytes(Paths.get(FILE)));
            return content;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
