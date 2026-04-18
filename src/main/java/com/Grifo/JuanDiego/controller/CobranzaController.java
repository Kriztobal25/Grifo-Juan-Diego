package com.Grifo.JuanDiego.controller;

import com.Grifo.JuanDiego.service.CobranzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cobranzas")
public class CobranzaController {

    @PostMapping("/abrir-gestion")
    public String abrirCobranza(@RequestParam String ruc, RedirectAttributes flash) {
        cobranzaService.crearNuevaGestion(ruc);
        flash.addFlashAttribute("success", "Se ha abierto un nuevo periodo de cobranza para la empresa.");
        return "redirect:/cobranzas";
    }

    @Autowired
    private CobranzaService cobranzaService;

    @GetMapping
    public String verPanel(Model model) {
        model.addAttribute("cobranzas", cobranzaService.listarTodas());
        return "cobranzas/panel";
    }

    @PostMapping("/vencer/{id}")
    public String marcarVencido(@PathVariable Long id, RedirectAttributes flash) {
        cobranzaService.marcarComoVencido(id);
        flash.addFlashAttribute("info", "La cuenta se marcó como VENCIDA. Ahora aplicará el 2% de mora mensual.");
        return "redirect:/cobranzas";
    }
}