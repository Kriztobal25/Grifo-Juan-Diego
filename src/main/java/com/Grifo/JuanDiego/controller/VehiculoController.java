package com.Grifo.JuanDiego.controller;

import com.Grifo.JuanDiego.model.Vehiculo;
import com.Grifo.JuanDiego.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/vehiculos")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Vehiculo vehiculo, RedirectAttributes flash) {
        try {
            vehiculoService.registrarOActualizarVehiculo(vehiculo);
            flash.addFlashAttribute("success", "Vehículo registrado con éxito.");
        } catch (RuntimeException e) {
            flash.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/vehiculos";
    }
}