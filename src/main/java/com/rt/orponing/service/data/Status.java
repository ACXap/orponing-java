// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service.data;

import java.util.Date;

public class Status {
    private final StatusType _status;
    private final Date _dateStatus;
    private final String _message;

    public Status(StatusType status, Date date, String message) {
        _status = status;
        _dateStatus = date;
        _message = message;
    }

    public StatusType getStatus() {
        return _status;
    }

    public Date getDateStatus() {
        return _dateStatus;
    }

    public String getMessage() {
        return _message;
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

    public static class StatusMessage {
        public final static String STOP = "Мне ничего не поручали";
        public final static String START = "Мне недосуг";
        public final static String NO_WORK = "Мне нечего делать";
        public final static String ERROR = "Ой, я упал. Поднимите меня";
        public final static String COMPLETED = "Я все завершил. Я КрасафчеГ!";
    }
}