// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class Status {
    private final StatusType status;
    private final Date dateStatus;
    private final String message;

    public String getMessage() {
        return message;
    }

    public static Status Start(String message) {
        if(message == null) message = StatusMessage.START;

        return new Status(StatusType.START, new Date(), message);
    }

    public static Status Stop(String message) {
        if(message == null) message = StatusMessage.STOP;

        return new Status(StatusType.STOP, new Date(), message);
    }

    public static Status Error(String message) {
        if(message == null) message = StatusMessage.ERROR;

        return new Status(StatusType.ERROR, new Date(), message);
    }
}