// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service.statuservices;

import com.rt.orponing.dao.CommonDb;
import com.rt.orponing.service.data.Status;
import com.rt.orponing.service.data.StatusMessage;

public abstract class CommonStatusServiceDb extends StatusService {

    public CommonStatusServiceDb(CommonDb db){
        this.db = db;
    }

    protected final CommonDb db;

    @Override
    public Status getStatus() {
        try {
            boolean result = db.TestDb();
            if (result) {
                status = Status.Start(StatusMessage.START);
            } else {
                status = Status.Error(StatusMessage.ERROR + " Тестовый прогон завершен некорректно.");
            }
        } catch (Exception ex) {
            status = Status.Error(StatusMessage.ERROR + " " + ex.getMessage());
        }

        return status;
    }
}