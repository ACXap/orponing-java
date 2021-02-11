// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service.statuservices;

import com.rt.orponing.dao.CommonDb;
import com.rt.orponing.service.data.InfoService;
import com.rt.orponing.service.data.Status;
import com.rt.orponing.service.data.StatusMessage;

public class StatusServiceDb extends AbstractStatusService {

    public StatusServiceDb(CommonDb db, InfoService infoService){
        this.db = db;
        this.infoService = infoService;
    }

    protected final CommonDb db;

    @Override
    public Status getStatus() {
        try {
            status = db.TestDb() ? Status.Start(StatusMessage.START) : Status.Error(StatusMessage.ERROR + " Тестовый прогон завершен некорректно.");
        } catch (Exception ex) {
            status = Status.Error(StatusMessage.ERROR + " " + ex.getMessage());
        }

        return status;
    }
}