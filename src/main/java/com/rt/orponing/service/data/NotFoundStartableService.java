// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service.data;

import com.rt.orponing.service.interfaces.IStartable;

public class NotFoundStartableService implements IStartable {

    private final Status status = Status.Error("Not found service");

    @Override
    public Status start() {
        return status;
    }

    @Override
    public String getId() {
        return "not-found-service";
    }
}