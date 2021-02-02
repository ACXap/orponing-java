// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service;

import com.rt.orponing.service.data.Status;

public class NotFoundStartableService implements IStartable{

    private final Status _status = Status.Error("Not found service");

    @Override
    public Status start() {
        return _status;
    }

    @Override
    public String getId() {
        return "not-found-service";
    }
}