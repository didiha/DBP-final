package com.example.dbpfinal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {
    @GetMapping("/")
    public String root() {
        return "redirect:/store/searching";
    }
}
