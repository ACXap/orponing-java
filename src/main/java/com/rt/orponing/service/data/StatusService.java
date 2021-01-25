package com.rt.orponing.service.data;

import java.util.Date;

public class StatusService {
    public final StatusType Status;
    public final Date DateStatus;
    public final String Error;

    private StatusService(StatusType status, Date date, String error) {
        Status = status;
        DateStatus = date;
        Error = error;
    }
    private StatusService(StatusType status) {
        Status = status;
        DateStatus = new Date();
        Error = null;
    }

    public static StatusService Start(){
        return new StatusService(StatusType.START, new Date(), null);
    }

    public static StatusService Stop(){
        return new StatusService(StatusType.STOP, new Date(), null);
    }

    public static StatusService Error(String error){
        return new StatusService(StatusType.ERROR, new Date(), error);
    }
}