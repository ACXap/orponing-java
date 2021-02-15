// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service;

import com.rt.orponing.service.data.Status;
import com.rt.orponing.service.data.StatusMessage;
import com.rt.orponing.service.data.StatusType;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Lazy
public class LogReadService {

    //region PrivateField

    private static final String PATH_LOGS = "logs";
    private static final String PATH_ARCHIVED = "logs/archived";
    private static final String FILE = "logs/logger.log";

    //endregion PrivateField

    //region PublicMethod

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
            return Files.walk(Paths.get(PATH_ARCHIVED)).filter(Files::isRegularFile).map(p -> p.getFileName().toString().replace(".log", "")).collect(Collectors.toList());
        } catch (IOException e) {
            return null;
        }
    }

    public Status clearArchive() throws RuntimeException {
        try {
            Files.walk(Paths.get(PATH_ARCHIVED))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .forEach(File::delete);

            return new Status(StatusType.COMPLETED, new Date(), StatusMessage.COMPLETED);
        } catch (IOException e) {
            return new Status(StatusType.ERROR, new Date(), e.getMessage());
        }
    }

    //endregion PublicMethod

    //region PrivateMethod

    private String read(String file) {
        try {
            return new String(Files.readAllBytes(Paths.get(file)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return null;
        }
    }

    //endregion PrivateMethod
}