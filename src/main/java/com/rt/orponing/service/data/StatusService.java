package com.rt.orponing.service.data;

import java.util.Date;

public class StatusService {
    public final StatusType Status;
    public final Date DateStatus;
    public final String Message;

    private StatusService(StatusType status, Date date, String message) {
        Status = status;
        DateStatus = date;
        Message = message;
    }

    public static StatusService Start(String message) {
        if(message == null) message = StatusMessage.START;

        return new StatusService(StatusType.START, new Date(), message);
    }

    public static StatusService Stop(String message) {
        if(message == null) message = StatusMessage.STOP;

        return new StatusService(StatusType.STOP, new Date(), message);
    }

    public static StatusService Error(String message) {
        if(message == null) message = StatusMessage.ERROR;

        return new StatusService(StatusType.ERROR, new Date(), message);
    }

    public static class StatusMessage {
        public static String STOP = "Мне ничего не поручали";
        public static String START = "Мне недосуг";
        public static String NO_WORK = "Мне нечего делать";
        public static String ERROR = "Ой, я упал. Поднимите меня";
    }
}