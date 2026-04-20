package com.Grifo.JuanDiego.controller;

import com.Grifo.JuanDiego.model.Catalogo_Combustible;
import com.Grifo.JuanDiego.service.CatalogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
@RequestMapping("/combustibles")
public class CatalogoController {

    @Autowired
    private CatalogoService catalogoService;

    @GetMapping
    public String verCatalogo(Model model) {
        // Enviamos la lista de combustibles registrados
        model.addAttribute("combustibles", catalogoService.listarTodos());
        
        // VITAL: Enviamos un objeto vacío para que el formulario th:object lo reconozca
        if (!model.containsAttribute("combustible")) {
            model.addAttribute("combustible", new Catalogo_Combustible());
        }
        
        return "combustibles/combustibles"; 
    }

    @PostMapping("/guardar") 
    public String registrarCombustible(@ModelAttribute("combustible") Catalogo_Combustible combustible, 
                                       RedirectAttributes flash) {
        try {
            catalogoService.registrarNuevo(combustible);
            flash.addFlashAttribute("success", "Combustible '" + combustible.getNombre() + "' guardado con éxito.");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al guardar: " + e.getMessage());
        }
        return "redirect:/combustibles";
    }

    @GetMapping("/editar/{id}")
    public String editarCombustible(@PathVariable("id") Integer id, Model model) {
        Catalogo_Combustible combustible = catalogoService.buscarPorId(id);
        if (combustible == null) {
            return "redirect:/combustibles";
        }
        model.addAttribute("combustible", combustible);
        model.addAttribute("combustibles", catalogoService.listarTodos());
        return "combustibles/combustibles"; // Retornamos la misma vista pero con los datos cargados
    }

    @PostMapping("/actualizar")
    public String actualizarPrecio(@RequestParam("id") Integer id, 
                                   @RequestParam("precioVenta") BigDecimal nuevoPrecio, 
                                   RedirectAttributes flash) {
        try {
            catalogoService.actualizarPrecio(id, nuevoPrecio);
            flash.addFlashAttribute("success", "Precio actualizado correctamente.");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "No se pudo actualizar el precio: " + e.getMessage());
        }
        return "redirect:/combustibles";
    }
}