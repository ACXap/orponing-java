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