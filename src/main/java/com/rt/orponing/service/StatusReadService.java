package com.rt.orponing.service;

import com.rt.orponing.dao.DbMdmSaveData;
import com.rt.orponing.repository.data.AddressInfo;
import com.rt.orponing.repository.data.EntityAddress;
import com.rt.orponing.service.data.StatusService;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class StatusReadService {
    public StatusReadService(OrponingTableService ots, OrponingService os, DbMdmSaveData db) {
        _ots = ots;
        _os = os;
        _db = db;
    }

    private final OrponingTableService _ots;
    private final OrponingService _os;
    private final DbMdmSaveData _db;

    private StatusService _otsStatus;
    private StatusService _osStatus;
    private StatusService _dbStatus;

    private static final String _testAddress = "Новосибирск г., Орджоникидзе ул., дом 18";
    private static final long _testGlobalId = 29182486;

    private static final long _pauseStatus = 300;

    public StatusService getStatusOrponingTableService() {

        if(isTime(_otsStatus) ){
            _otsStatus = _ots.getStatusService();
        }

        return _otsStatus;
    }

    public StatusService getStatusOrponingService() {

        // тут надо проверять что статус не позднее 5 минут
        if(isTime(_osStatus)){
            AddressInfo addressInfo = _os.OrponingAddress(new EntityAddress(1, _testAddress));

            if (addressInfo.GlobalId == _testGlobalId) {
                _osStatus = StatusService.Start(StatusService.StatusMessage.START);
            } else {
                _osStatus = StatusService.Error(StatusService.StatusMessage.ERROR + " Тестовые данные не совпадают. Сервис работает некорректно. " + addressInfo.Error);
            }
        }

        return _osStatus;
    }

    public StatusService getStatusDbSaveData() {

        if(isTime(_dbStatus)){
           try {
               boolean result = _db.TestDb();
               if(result){
                   _dbStatus = StatusService.Start(StatusService.StatusMessage.START);
               } else{
                   _dbStatus = StatusService.Error(StatusService.StatusMessage.ERROR + " Тестовый прогон завершен некорректно." );
               }

           } catch (Exception ex) {
               _dbStatus = StatusService.Error(StatusService.StatusMessage.ERROR + " " + ex.getMessage());
           }
       }

        return _dbStatus;
    }

    private boolean isTime(StatusService statusService){
        if(statusService== null || statusService.DateStatus == null) return  true;

        return new Date().getTime() - statusService.DateStatus.getTime() < _pauseStatus;
    }
}