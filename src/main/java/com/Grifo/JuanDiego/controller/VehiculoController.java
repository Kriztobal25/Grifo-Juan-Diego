package com.Grifo.JuanDiego.controller;

import com.Grifo.JuanDiego.model.Vehiculo;
import com.Grifo.JuanDiego.service.VehiculoService;
import com.Grifo.JuanDiego.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/vehiculos")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("vehiculos", vehiculoService.listarTodos());
        model.addAttribute("clientes", clienteService.listarTodos());
        
        if (!model.containsAttribute("vehiculo")) {
            model.addAttribute("vehiculo", new Vehiculo());
        }
        return "vehiculos/vehiculos"; 
    }

    // ESTE MÉTODO SOLUCIONA EL ERROR 404 AL EDITAR
    @GetMapping("/editar/{placa}")
    public String editar(@PathVariable("placa") String placa, Model model) {
        Vehiculo vehiculo = vehiculoService.buscarPorPlaca(placa);
        if (vehiculo == null) {
            return "redirect:/vehiculos";
        }
        model.addAttribute("vehiculo", vehiculo);
        model.addAttribute("vehiculos", vehiculoService.listarTodos());
        model.addAttribute("clientes", clienteService.listarTodos()); // Carga el select para la edición
        return "vehiculos/vehiculos";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("vehiculo") Vehiculo vehiculo, RedirectAttributes flash) {
        try {
            vehiculoService.registrarOActualizarVehiculo(vehiculo);
            flash.addFlashAttribute("success", "Vehículo procesado con éxito.");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/vehiculos";
    }

    @PostMapping("/eliminar/{placa}")
    public String eliminar(@PathVariable("placa") String placa, RedirectAttributes flash) {
        try {
            vehiculoService.eliminarVehiculo(placa);
            flash.addFlashAttribute("success", "Unidad eliminada correctamente.");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "No se puede eliminar la unidad.");
        }
        return "redirect:/vehiculos";
    }
}