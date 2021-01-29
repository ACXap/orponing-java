// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service.statuservices;

import com.rt.orponing.repository.data.AddressInfo;
import com.rt.orponing.repository.data.EntityAddress;
import com.rt.orponing.service.OrponingService;
import com.rt.orponing.service.data.Status;
import org.springframework.stereotype.Component;

@Component
public class StatusServiceOrponService extends StatusService {

    public StatusServiceOrponService(OrponingService _os) {
        this._os = _os;

        _name = "Сервис для разбора адресов";
        _id = "orponing";
        _icon= "server";
    }

    private final OrponingService _os;
    private static final String _testAddress = "Новосибирск г., Орджоникидзе ул., дом 18";
    private static final long _testGlobalId = 29182486;

    @Override
    public Status getStatus() {
        AddressInfo addressInfo = _os.OrponingAddress(new EntityAddress(1, _testAddress));

        if (addressInfo.GlobalId == _testGlobalId) {
            _status = Status.Start(Status.StatusMessage.START);
        } else {
            _status = Status.Error(Status.StatusMessage.ERROR + " Тестовые данные не совпадают. Сервис работает некорректно. " + addressInfo.Error);
        }

        return _status;
    }
}