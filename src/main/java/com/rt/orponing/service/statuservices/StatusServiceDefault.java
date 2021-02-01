// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service.statuservices;

import com.rt.orponing.service.data.Status;

public class StatusServiceDefault extends StatusService {

    public StatusServiceDefault() {
        _name = "Сервис не найден";
        _id = "not-found-service";
        _icon = "error";

        _status = Status.Error("Not found service");
    }

    @Override
    public Status getStatus() {
        return _status;
    }
}