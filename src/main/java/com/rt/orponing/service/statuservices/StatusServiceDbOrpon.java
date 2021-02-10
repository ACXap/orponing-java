// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service.statuservices;

import com.rt.orponing.dao.CommonDb;
import com.rt.orponing.service.data.InfoService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class StatusServiceDbOrpon extends CommonStatusServiceDb {

    public StatusServiceDbOrpon(@Qualifier("dbOrponAddress") CommonDb db) {
        super(db);
        infoService = new InfoService("БД со справочником ГИД - Адрес", "db-address", "database", "БД со справочником ГИД - Адрес", false);
    }
}