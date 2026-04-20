package com.Grifo.JuanDiego.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    // Única ruta: http://localhost:8080/login
    @GetMapping("/login")
    public String verLogin() {
        return "login";
    }
}