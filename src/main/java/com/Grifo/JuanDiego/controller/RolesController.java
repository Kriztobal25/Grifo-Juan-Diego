package com.Grifo.JuanDiego.controller;

import com.Grifo.JuanDiego.model.Roles;
import com.Grifo.JuanDiego.service.RolService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/configuracion/roles")
public class RolesController {

    @PostMapping("/registrar")
    public String registrarRol(@ModelAttribute Roles rol, RedirectAttributes flash) {
        rolService.guardar(rol);
        flash.addFlashAttribute("success", "Nuevo rol registrado.");
        return "redirect:/configuracion/roles";
    }

    @Autowired
    private RolService rolService;

    @GetMapping
    public String listarRoles(Model model) {
        model.addAttribute("roles", rolService.listarRoles());
        return "configuracion/roles";
    }
}