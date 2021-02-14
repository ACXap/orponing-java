// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service;

import com.rt.orponing.service.data.*;
import com.rt.orponing.service.interfaces.IStartable;
import com.rt.orponing.service.statuservices.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@Service
@Lazy
public class ManagerService {
    public ManagerService(List<AbstractStatusService> listAbstractStatusServices, List<IStartable> listStartableService) {
        statusServices = listAbstractStatusServices.stream().collect(toMap(s -> s.getInfoService().getId(), s -> s));
        startableService = listStartableService.stream().collect(toMap(IStartable::getId, s -> s));
    }

    private final Map<String, AbstractStatusService> statusServices;
    private final Map<String, IStartable> startableService;

    public AbstractStatusService getStatusService(String id) {
        return statusServices.getOrDefault(id, new StatusServiceDefault());
    }

    public IStartable getStartableService(String id) {
        return startableService.getOrDefault(id, new NotFoundStartableService());
    }

    public List<InfoService> getAllInfoService() {
        return statusServices.values().stream().map(AbstractStatusService::getInfoService).collect(Collectors.toList());
    }

    public Status getStatus(String id) {
        return getStatusService(id).getStatus();
    }
}