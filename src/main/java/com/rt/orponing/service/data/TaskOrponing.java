// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service.data;

import com.rt.orponing.repository.data.AddressInfo;
import com.rt.orponing.repository.data.EntityAddress;
import lombok.Getter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
public class TaskOrponing {

    public TaskOrponing(UUID id, List<EntityAddress> listAddressRequest) {
        this.id = id;
        this.listAddressRequest = listAddressRequest;
        statusTask = Status.Stop(Status.StatusMessage.STOP);
    }

    //region PrivateField

    private final UUID id;
    private Status statusTask;
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
        statusTask = Status.Start(Status.StatusMessage.START);
        dateStart = new Date();
    }

    public void errorTask(String message){
        statusTask = Status.Error(Status.StatusMessage.ERROR + " " + message);
        dateStop = new Date();
    }

    public void stopTask(){
        statusTask = new Status(StatusType.COMPLETED, new Date(), Status.StatusMessage.COMPLETED);
        dateStop = new Date();
    }
    //endregion PublicMethod
}