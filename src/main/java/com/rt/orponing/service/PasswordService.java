// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service;

import com.rt.orponing.service.data.Status;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Lazy
public class PasswordService {

    @Value("${log.clear.password}")
    private String passwordLogClear;

    public Status clearLog(String password){
        if (!password.equals(passwordLogClear)) return Status.Error("Password no correct");

        return Status.Start("OK");
    }
}