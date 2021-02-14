// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service.data;

import com.rt.orponing.repository.data.AddressInfo;
import com.rt.orponing.repository.data.EntityAddress;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class TaskOrponing {

    //region PrivateField

    private final UUID id;
    private Status statusTask = Status.Stop(StatusMessage.STOP);
    private final Date dataCreate = new Date();
    private Date dateStart;
    private Date dateStop;
    private final List<EntityAddress> listAddressRequest;
    private List<AddressInfo> listAddressResponse;

    //endregion PrivateField

    //region PublicProperty
    public void setResult(List<AddressInfo> result){
        listAddressResponse = result;
    }
    //endregion PublicProperty

    //region PublicMethod
    public void startTask(){
        statusTask = Status.Start(StatusMessage.START);
        dateStart = new Date();
    }

    public void errorTask(String message){
        statusTask = Status.Error(StatusMessage.ERROR + " " + message);
        dateStop = new Date();
    }

    public void stopTask(){
        statusTask = new Status(StatusType.COMPLETED, new Date(), StatusMessage.COMPLETED);
        dateStop = new Date();
    }
    //endregion PublicMethod
}