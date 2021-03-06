// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.controllers.views;

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