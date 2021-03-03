// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service.data;

import com.rt.orponing.repository.data.AddressInfo;
import com.rt.orponing.repository.data.EntityAddress;
import com.rt.orponing.repository.data.ResponseAddressInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    private List<ResponseAddressInfo> listAddressResponse;

    //endregion PrivateField

    //region PublicProperty
    public void setResult(List<AddressInfo> result){

        listAddressResponse = listAddressRequest.parallelStream()
                .map(ra -> new ResponseAddressInfo(ra, result.stream().filter(a->a.Id == ra.Id).findFirst().orElseGet(()-> new AddressInfo(ra.Id, "Not found id")) ))
                .collect(Collectors.toList());
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