package com.Grifo.JuanDiego.controller;

import java.util.List; 
import java.util.ArrayList;
import com.Grifo.JuanDiego.model.Cobranza;
import com.Grifo.JuanDiego.service.CobranzaService;
import com.Grifo.JuanDiego.service.EmpresaService;
import com.Grifo.JuanDiego.service.PagoService;
import com.Grifo.JuanDiego.model.Pagos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cobranzas")
public class CobranzaController {

    @Autowired
    private CobranzaService cobranzaService;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private PagoService pagoService;

    @GetMapping
    public String listar(Model model) {
        // 1. Obtenemos la lista de cobranzas
        List<Cobranza> lista = cobranzaService.listarTodas();
        if (lista == null) {
            lista = new ArrayList<>();
        }

        // 2. CÁLCULO DEL TOTAL (Solución al error del método faltante en el Service)
        Double total = lista.stream()
                .filter(c -> c.getMontoPendiente() != null)
                .mapToDouble(c -> c.getMontoPendiente().doubleValue())
                .sum();

        // 3. CONTEO DE MORA (Solución al error de tipos incompatibles long -> Integer)
        // El método .count() devuelve un long, por eso lo casteamos a (int)
        long moraLong = lista.stream()
                .filter(c -> c.getEstadoCobranza() != null && 
                             "Vencido".equalsIgnoreCase(c.getEstadoCobranza().toString()))
                .count();
        
        Integer mora = (int) moraLong; 

        // 4. PASAMOS LOS ATRIBUTOS AL MODELO
        model.addAttribute("cobranzas", lista);
        model.addAttribute("totalPorCobrar", total);
        model.addAttribute("conteoMora", mora);
        
        // Usamos el nombre del archivo real detectado en tu estructura de carpetas
        return "cobranzas/gestion-cobranza"; 
    }

    @GetMapping("/registrar-pago")
    public String verRegistroPago(Model model) {
        model.addAttribute("pago", new Pagos());
        model.addAttribute("cobranzasActivas", cobranzaService.listarTodas());
        return "cobranzas/registro-pago";
    }
}