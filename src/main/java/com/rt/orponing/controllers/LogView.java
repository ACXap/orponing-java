package com.rt.orponing.controllers;

import com.rt.orponing.service.LogReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Lazy
@RequiredArgsConstructor
public class LogView {
    private final LogReadService service;

    @GetMapping("/log")
    public String logPage(Model model) {
        model.addAttribute("archive", service.getDayLog());
        return "log";
    }
}