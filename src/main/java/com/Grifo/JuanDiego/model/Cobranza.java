package com.Grifo.JuanDiego.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cobranza")
public class Cobranza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cobranza")
    private Long idCobranza;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ruc_empresa")
    private Empresa empresa;

    @Column(name = "monto_pendiente")
    private BigDecimal montoPendiente = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_cobranza")
    private EstadoCobranza estadoCobranza = EstadoCobranza.Pendiente;

    public enum EstadoCobranza { Pendiente, Pagado, Vencido }

    // --- CONSTRUCTORES CORREGIDOS ---
    public Cobranza() {} // Constructor vacío obligatorio para JPA

    public Cobranza(Empresa empresa, BigDecimal montoPendiente, EstadoCobranza estadoCobranza) {
        this.empresa = empresa;
        this.montoPendiente = montoPendiente;
        this.estadoCobranza = estadoCobranza;
    }

    // --- GETTERS Y SETTERS ---
    public Long getIdCobranza() { return idCobranza; }
    public void setIdCobranza(Long idCobranza) { this.idCobranza = idCobranza; }
    public Empresa getEmpresa() { return empresa; }
    public void setEmpresa(Empresa empresa) { this.empresa = empresa; }
    public BigDecimal getMontoPendiente() { return montoPendiente; }
    public void setMontoPendiente(BigDecimal montoPendiente) { this.montoPendiente = montoPendiente; }
    public EstadoCobranza getEstadoCobranza() { return estadoCobranza; }
    public void setEstadoCobranza(EstadoCobranza estadoCobranza) { this.estadoCobranza = estadoCobranza; }
}