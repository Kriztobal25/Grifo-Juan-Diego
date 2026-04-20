package com.Grifo.JuanDiego.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "empresa")
public class Empresa {

    @Id
    @Column(name = "ruc", length = 11) // El RUC en Perú siempre tiene 11 caracteres
    private String ruc;

    @Column(name = "razon_social", nullable = false, length = 150)
    private String razonSocial;

    @Column(name = "limite_credito_galones")
    private BigDecimal limiteCreditoGalones;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_empresa")
    private EstadoEmpresa estadoEmpresa = EstadoEmpresa.Activo;

    public enum EstadoEmpresa { Activo, Suspendido }

    // Getters y Setters
    public String getRuc() { return ruc; }
    public void setRuc(String ruc) { this.ruc = ruc; }
    
    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }
    
    public BigDecimal getLimiteCreditoGalones() { return limiteCreditoGalones; }
    public void setLimiteCreditoGalones(BigDecimal limiteCreditoGalones) { this.limiteCreditoGalones = limiteCreditoGalones; }
    
    public EstadoEmpresa getEstadoEmpresa() { return estadoEmpresa; }
    public void setEstadoEmpresa(EstadoEmpresa estadoEmpresa) { this.estadoEmpresa = estadoEmpresa; }
}