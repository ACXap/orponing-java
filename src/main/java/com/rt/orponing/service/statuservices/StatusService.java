// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service.statuservices;

import com.rt.orponing.service.interfaces.IStatus;
import com.rt.orponing.service.data.InfoService;
import com.rt.orponing.service.data.Status;

public class StatusService extends AbstractStatusService {

    public StatusService(IStatus iStatus, InfoService infoService) {
        this.iStatus = iStatus;
        this.infoService = infoService;
    }

    private final IStatus iStatus;

    @Override
    public Status getStatus() {
        status = iStatus.getStatus();
        return status;
    }
}