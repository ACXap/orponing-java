// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service.statuservices;

import com.rt.orponing.dao.CommonDb;
import com.rt.orponing.dao.DbMdmSaveData;
import com.rt.orponing.service.data.InfoService;
import com.rt.orponing.service.data.Status;
import com.rt.orponing.service.data.StatusMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class StatusServiceDbMdm extends CommonStatusServiceDb {

    public StatusServiceDbMdm(@Qualifier("dbMdmSaveData") CommonDb db) {
       super(db);
        infoService = new InfoService("БД для хранения разобранных адресов", "db", "database", "БД где хранятся адреса для орпонизации", false);
    }
}