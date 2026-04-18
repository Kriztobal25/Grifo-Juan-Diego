package com.Grifo.JuanDiego.controller;

import com.Grifo.JuanDiego.service.EmpleadoService;
import com.Grifo.JuanDiego.model.Empleado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {

    @PostMapping("/registrar")
    public String registrarEmpleado(@ModelAttribute Empleado empleado, RedirectAttributes flash) {
        try {
            empleadoService.guardar(empleado);
            flash.addFlashAttribute("success", "Empleado registrado correctamente en la planilla.");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error: El DNI ya existe.");
        }
        return "redirect:/empleados";
    }

    @Autowired
    private EmpleadoService empleadoService;

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam String dni,
                           @RequestParam String adminUsername,
                           @RequestParam String adminPassword,
                           RedirectAttributes flash) {
        try {
            empleadoService.eliminarConSeguridad(dni, adminPassword, adminUsername);
            flash.addFlashAttribute("success", "Empleado eliminado correctamente.");
        } catch (Exception e) {
            flash.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/empleados";
    }
}