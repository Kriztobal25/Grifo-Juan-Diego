package com.Grifo.JuanDiego.controller;

import com.Grifo.JuanDiego.model.Credito;
import com.Grifo.JuanDiego.service.CreditoService;
import com.Grifo.JuanDiego.service.CatalogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/operaciones/despacho")
public class CreditoController {

    @Autowired
    private CreditoService creditoService;
    
    @Autowired
    private CatalogoService catalogoService;

    @GetMapping("/nuevo")
    public String formularioDespacho(Model model) {
        model.addAttribute("credito", new Credito());
        model.addAttribute("combustibles", catalogoService.listarTodos());
        return "operaciones/formulario-despacho";
    }

    @PostMapping("/registrar")
    public String registrarDespacho(@ModelAttribute Credito credito, RedirectAttributes flash) {
        try {
            creditoService.registrarDespacho(credito);
            flash.addFlashAttribute("success", "¡Despacho confirmado! La deuda ha sido cargada a la empresa correspondiente.");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error en el registro: " + e.getMessage());
        }
        return "redirect:/operaciones/despacho/nuevo";
    }
}