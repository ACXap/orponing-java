package com.rt.orponing.service.statuservices;

import com.rt.orponing.service.OrponingApiService;
import com.rt.orponing.service.data.InfoService;
import com.rt.orponing.service.data.Status;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class StatusServiceOrponingApi extends StatusService{

    public StatusServiceOrponingApi(OrponingApiService oas){
        this.oas = oas;
        infoService = new InfoService("Фоновый сервис API", "orponing-service-api", "server", "", false);
    }

    private final OrponingApiService oas;

    @Override
    public Status getStatus() {
        status = oas.getStatus();

        return status;
    }
}