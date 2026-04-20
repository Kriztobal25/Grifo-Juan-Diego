package com.Grifo.JuanDiego.controller;

import com.Grifo.JuanDiego.model.Pagos;
import com.Grifo.JuanDiego.model.Cobranza;
import com.Grifo.JuanDiego.service.PagoService;
import com.Grifo.JuanDiego.service.CobranzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cobranzas") 
public class PagoController {

    @Autowired
    private PagoService pagoService;
    
    @Autowired
    private CobranzaService cobranzaService;

    @GetMapping("/pago")
    public String verFormularioPago(Model model) {
        model.addAttribute("pago", new Pagos());
        return "cobranzas/registro-pago"; 
    }

    /**
     * Procesa el guardado del pago y la liquidación de la deuda.
     */
    @PostMapping("/registrar-pago")
    public String procesarPago(@ModelAttribute("pago") Pagos pago, 
                               @RequestParam("archivoVoucher") MultipartFile archivo, 
                               RedirectAttributes flash) {
        try {
            pagoService.registrarPago(pago, archivo); 
            flash.addFlashAttribute("success", "Pago registrado con éxito. La deuda ha sido liquidada.");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al procesar el pago: " + e.getMessage());
        }
        return "redirect:/cobranzas"; 
    }
}