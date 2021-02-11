// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service.statuservices;

import com.rt.orponing.service.data.InfoService;
import com.rt.orponing.service.data.Status;

public abstract class AbstractStatusService {

    protected InfoService infoService;
    protected Status status;

    public InfoService getInfoService(){
        return  infoService;
    }

    public abstract Status getStatus();
}