package com.rt.orponing.controllers;

import com.rt.orponing.dao.data.DaoException;
import com.rt.orponing.service.OrponingTableService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrponingServiceViewController {

    public OrponingServiceViewController(OrponingTableService service) {
        _service = service;
    }

    private final OrponingTableService _service;

    @GetMapping("/orponing_service")
    public String orponingService(Model model) throws DaoException {

        model.addAttribute("status", _service.getStatusService());
        model.addAttribute("urlStart", "/orponing_service/start - запуск сервиса");
        model.addAttribute("urlStop", "/orponing_service/status - проверка статуса сервиса");

        return "orponing_service";
    }
}