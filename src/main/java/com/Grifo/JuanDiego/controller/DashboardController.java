package com.Grifo.JuanDiego.controller;

import com.Grifo.JuanDiego.service.CreditoService;
import com.Grifo.JuanDiego.service.CobranzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
public class DashboardController {

    @Autowired
    private CreditoService creditoService;

    @Autowired
    private CobranzaService cobranzaService;

    // EL ÚNICO DUEÑO DE LA RAÍZ "/"
    @GetMapping("/")
    public String index(Model model) {
        // 1. Suma de galones de hoy
        // Agregamos un filter extra para evitar NullPointerException si cantidad_galones es null
        BigDecimal galonesHoy = creditoService.listarTodos().stream()
                .filter(c -> c.getFechaInicio() != null && c.getFechaInicio().toLocalDate().equals(LocalDate.now()))
                .map(c -> c.getCantidadGalones() != null ? c.getCantidadGalones() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 2. Deuda Total (Viene de CobranzaService)
        BigDecimal deudaGlobal = cobranzaService.calcularDeudaTotal();
        if (deudaGlobal == null) deudaGlobal = BigDecimal.ZERO;

        // 3. Conteo de Vales Vencidos
        long vencidos = cobranzaService.contarEmpresasEnMora();

        // Enviamos los datos reales al Dashboard
        model.addAttribute("totalGalonesHoy", galonesHoy);
        model.addAttribute("totalDeudaGlobal", deudaGlobal);
        model.addAttribute("conteoVencidos", vencidos);

        return "index";
    }
}