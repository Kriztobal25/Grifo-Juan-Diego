package com.Grifo.JuanDiego.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Empresa {
    @Id
    private String ruc;
    private String razonSocial;
    private BigDecimal limiteCreditoGalones;
    
    @Enumerated(EnumType.STRING)
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