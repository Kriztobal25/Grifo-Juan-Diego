package com.Grifo.JuanDiego.controller;

import com.Grifo.JuanDiego.model.Cobranza;
import com.Grifo.JuanDiego.service.CobranzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cobranzas")
public class RestCobranzaController {

    @Autowired
    private CobranzaService cobranzaService;

    @GetMapping("/pendientes")
    public List<CobranzaDTO> listarPendientes() {
        return cobranzaService.listarTodas().stream()
                .filter(c -> c.getMontoPendiente() != null && c.getMontoPendiente().doubleValue() > 0)
                .map(c -> new CobranzaDTO(
                        c.getIdCobranza(),
                        c.getEmpresa().getRuc(),
                        c.getEmpresa().getRazonSocial(),
                        c.getMontoPendiente().doubleValue(),
                        c.getEstadoCobranza().toString()
                ))
                .collect(Collectors.toList());
    }

    public static class CobranzaDTO {
        public Long id;
        public String ruc;
        public String razonSocial;
        public double monto;
        public String estado;

        public CobranzaDTO(Long id, String ruc, String razonSocial, double monto, String estado) {
            this.id = id;
            this.ruc = ruc;
            this.razonSocial = razonSocial;
            this.monto = monto;
            this.estado = estado;
        }
    }
}