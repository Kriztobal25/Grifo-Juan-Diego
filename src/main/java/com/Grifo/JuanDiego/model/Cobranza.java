package com.Grifo.JuanDiego.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Cobranza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCobranza;

    @ManyToOne
    @JoinColumn(name = "ruc_empresa")
    private Empresa empresa;

    private BigDecimal montoPendiente = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private EstadoCobranza estadoCobranza = EstadoCobranza.Pendiente;

    public enum EstadoCobranza { Pendiente, Pagado, Vencido }

    // Getters y Setters Manuales
    public Long getIdCobranza() { return idCobranza; }
    public void setIdCobranza(Long idCobranza) { this.idCobranza = idCobranza; }
    public Empresa getEmpresa() { return empresa; }
    public void setEmpresa(Empresa empresa) { this.empresa = empresa; }
    public BigDecimal getMontoPendiente() { return montoPendiente; }
    public void setMontoPendiente(BigDecimal montoPendiente) { this.montoPendiente = montoPendiente; }
    public EstadoCobranza getEstadoCobranza() { return estadoCobranza; }
    public void setEstadoCobranza(EstadoCobranza estadoCobranza) { this.estadoCobranza = estadoCobranza; }
}