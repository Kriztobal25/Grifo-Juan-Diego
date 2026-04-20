package com.Grifo.JuanDiego.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
public class Pagos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Long idPago;

    @ManyToOne
    @JoinColumn(name = "id_cobranza", referencedColumnName = "id_cobranza")
    private Cobranza cobranza;

    @ManyToOne
    @JoinColumn(name = "dni_empleado")
    private Empleado empleado;

    @Column(name = "monto_pagado")
    private BigDecimal montoPagado;

    @Column(name = "fecha_pago")
    private LocalDateTime fechaPago = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago")
    private MetodoPago metodoPago;

    @Column(name = "nro_operacion", length = 50)
    private String nroOperacion;

    @Column(name = "comprobante", length = 255)
    private String comprobante;

    public enum MetodoPago { Yape, Ágora, Transferencia_Bancaria, Efectivo }

    // Getters y Setters
    public Long getIdPago() { return idPago; }
    public void setIdPago(Long idPago) { this.idPago = idPago; }
    
    public Cobranza getCobranza() { return cobranza; }
    public void setCobranza(Cobranza cobranza) { this.cobranza = cobranza; }
    
    public Empleado getEmpleado() { return empleado; }
    public void setEmpleado(Empleado empleado) { this.empleado = empleado; }
    
    public BigDecimal getMontoPagado() { return montoPagado; }
    public void setMontoPagado(BigDecimal montoPagado) { this.montoPagado = montoPagado; }
    
    public LocalDateTime getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDateTime fechaPago) { this.fechaPago = fechaPago; }
    
    public MetodoPago getMetodoPago() { return metodoPago; }
    public void setMetodoPago(MetodoPago metodoPago) { this.metodoPago = metodoPago; }
    
    public String getNroOperacion() { return nroOperacion; }
    public void setNroOperacion(String nroOperacion) { this.nroOperacion = nroOperacion; }
    
    public String getComprobante() { return comprobante; }
    public void setComprobante(String comprobante) { this.comprobante = comprobante; }
}