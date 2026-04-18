package com.Grifo.JuanDiego.controller;

import com.Grifo.JuanDiego.model.Pagos;
import com.Grifo.JuanDiego.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/finanzas/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @PostMapping("/registrar")
    public String procesarPago(@ModelAttribute Pagos pago, 
                               @RequestParam("voucher") MultipartFile archivo, 
                               RedirectAttributes flash) {
        try {
            // Aquí llamaríamos a un componente para guardar la imagen en disco
            // y pasaríamos la ruta al objeto pago.
            pagoService.registrarPago(pago);
            flash.addFlashAttribute("success", "Pago registrado y saldo actualizado correctamente.");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al procesar el pago: " + e.getMessage());
        }
        return "redirect:/cobranzas/panel";
    }
}