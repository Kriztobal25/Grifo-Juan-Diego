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
@RequestMapping("/configuracion/combustibles")
public class CatalogoController {

    @Autowired
    private CatalogoService catalogoService;

    @PostMapping("/registrar")
    public String registrarCombustible(@ModelAttribute Catalogo_Combustible combustible, RedirectAttributes flash) {
        catalogoService.registrarNuevo(combustible);
        flash.addFlashAttribute("success", "Nuevo combustible añadido.");
        return "redirect:/configuracion/combustibles";
    }

    @GetMapping
    public String verCatalogo(Model model) {
        model.addAttribute("combustibles", catalogoService.listarTodos());
        return "configuracion/combustibles";
    }

    @PostMapping("/actualizar")
    public String actualizarPrecio(@RequestParam Integer id, @RequestParam BigDecimal nuevoPrecio, RedirectAttributes flash) {
        catalogoService.actualizarPrecio(id, nuevoPrecio);
        flash.addFlashAttribute("success", "Precio actualizado");
        return "redirect:/configuracion/combustibles";
    }
}