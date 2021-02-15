// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Lazy
public class LogReadService {

    private static final String PATH_LOGS = "logs";
    private static final String PATH_ARCHIVED = "logs/archived/";
    private static final String FILE = "logs/logger.log";

    public String readLogToday() {
        return read(FILE);
    }

    public String readLog(String file) {
        File[] f1 = new File(PATH_LOGS).listFiles((dir, name) -> name.contains(file));
        File[] f2 = new File(PATH_ARCHIVED).listFiles((dir, name) -> name.contains(file));

        if (f1 != null && f1.length > 0) return read(f1[0].toString());
        if (f2 != null && f2.length > 0) return read(f2[0].toString());

        return null;
    }

    public List<String> getDayLog() {
        try {
            return Files.walk(Paths.get(PATH_ARCHIVED)).filter(Files::isRegularFile).map(Path::toString).collect(Collectors.toList());
        } catch (IOException e) {
            return null;
        }
    }

    public void clearArchive() {
        CompletableFuture.runAsync(() -> {
            try {
                Files.walk(Paths.get(PATH_ARCHIVED))
                        .filter(Files::isRegularFile)
                        .map(Path::toFile)
                        .forEach(File::deleteOnExit);
            } catch (IOException ignored) {
            }
        });
    }

    private String read(String file) {
        try {
            return new String(Files.readAllBytes(Paths.get(file)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return null;
        }
    }
}