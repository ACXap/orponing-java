// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service.statuservices;

import com.rt.orponing.dao.DbMdmSaveData;
import com.rt.orponing.service.data.InfoService;
import com.rt.orponing.service.data.Status;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class StatusServiceDbMdm extends StatusService {

    public StatusServiceDbMdm(DbMdmSaveData db) {
        this.db = db;
        infoService = new InfoService("БД для хранения разобранных адресов", "db", "database", "", false);
    }

    private final DbMdmSaveData db;

    @Override
    public Status getStatus() {
        try {
            boolean result = db.TestDb();
            if (result) {
                status = Status.Start(Status.StatusMessage.START);
            } else {
                status = Status.Error(Status.StatusMessage.ERROR + " Тестовый прогон завершен некорректно.");
            }
        } catch (Exception ex) {
            status = Status.Error(Status.StatusMessage.ERROR + " " + ex.getMessage());
        }

        return status;
    }
}