// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service.statuservices;

import com.rt.orponing.service.data.InfoService;
import com.rt.orponing.service.data.Status;

public class StatusServiceDefault extends AbstractStatusService {

    public StatusServiceDefault() {
        status = Status.Error("Not found service");
        infoService = new InfoService("Сервис не найден",
                "not-found-service",
                "error",
                "Увы, сервиса с таким ID не существует",
                false);
    }

    @Override
    public Status getStatus() {
        return status;
    }
}