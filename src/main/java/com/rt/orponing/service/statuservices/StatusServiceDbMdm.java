// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service.statuservices;

import com.rt.orponing.dao.DbMdmSaveData;
import com.rt.orponing.service.data.Status;
import org.springframework.stereotype.Component;

@Component
public class StatusServiceDbMdm extends StatusService {

    public StatusServiceDbMdm(DbMdmSaveData _db) {
        this._db = _db;

        _name = "БД для хранения разобранных адресов";
        _id = "db";
        _icon= "database";
    }

    private final DbMdmSaveData _db;

    @Override
    public Status getStatus() {
        try {
            boolean result = _db.TestDb();
            if (result) {
                _status = Status.Start(Status.StatusMessage.START);
            } else {
                _status = Status.Error(Status.StatusMessage.ERROR + " Тестовый прогон завершен некорректно.");
            }
        } catch (Exception ex) {
            _status = Status.Error(Status.StatusMessage.ERROR + " " + ex.getMessage());
        }

        return _status;
    }
}