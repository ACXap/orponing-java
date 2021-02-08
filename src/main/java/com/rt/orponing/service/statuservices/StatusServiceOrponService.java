// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service.statuservices;

import com.rt.orponing.repository.data.AddressInfo;
import com.rt.orponing.repository.data.EntityAddress;
import com.rt.orponing.service.OrponingService;
import com.rt.orponing.service.data.InfoService;
import com.rt.orponing.service.data.Status;
import com.rt.orponing.service.data.StatusMessage;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class StatusServiceOrponService extends StatusService {

    public StatusServiceOrponService(OrponingService os) {
        this.os = os;
        infoService = new InfoService("Сервис для разбора адресов", "orponing", "server", "Основной сервис который по адресу предоставляет информацию", false);
    }

    private final OrponingService os;
    private static final String _testAddress = "Новосибирск г., Орджоникидзе ул., дом 18";
    private static final long _testGlobalId = 29182486;

    @Override
    public Status getStatus() {
        AddressInfo addressInfo = os.OrponingAddress(new EntityAddress(1, _testAddress));

        if (addressInfo.GlobalId == _testGlobalId) {
            status = Status.Start(StatusMessage.START);
        } else {
            status = Status.Error(StatusMessage.ERROR + " Тестовые данные не совпадают. Сервис работает некорректно. " + addressInfo.Error);
        }

        return status;
    }
}