package com.Grifo.JuanDiego.controller;

import com.Grifo.JuanDiego.model.Roles;
import com.Grifo.JuanDiego.service.RolService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios/roles")
public class RolesController {

    @Autowired
    private RolService rolService;

    @GetMapping
    public String listarRoles(Model model) {
        model.addAttribute("roles", rolService.listarRoles());
        // CORRECCIÓN: Asegúrate de que el archivo sea templates/usuarios/roles.html
        return "usuarios/roles"; 
    }

    @PostMapping("/guardar")
    public String registrarRol(@ModelAttribute("rol") Roles rol, RedirectAttributes flash) {
        try {
            rolService.guardar(rol);
            flash.addFlashAttribute("success", "Nuevo rol registrado correctamente.");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al guardar el rol: " + e.getMessage());
        }
        return "redirect:/usuarios/roles";
    }
}