// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.controllers;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Lazy
public class MainController {
    @GetMapping
    public String mainPage() {
        return "main";
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "about";
    }

    @GetMapping("/log")
    public String logPage() {
        return "log";
    }

    @GetMapping("/orponing_service")
    public String orponingServicePage() {
        return "orponing_service";
    }
}