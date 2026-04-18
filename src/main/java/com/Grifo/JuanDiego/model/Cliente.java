package com.Grifo.JuanDiego.model;

import jakarta.persistence.*;

@Entity
public class Cliente {
    @Id
    private String dniCliente;
    
    @ManyToOne
    @JoinColumn(name = "ruc_empresa")
    private Empresa empresa;
    
    private String nombreChofer;

    // Getters y Setters Manuales
    public String getDniCliente() { return dniCliente; }
    public void setDniCliente(String dniCliente) { this.dniCliente = dniCliente; }
    public Empresa getEmpresa() { return empresa; }
    public void setEmpresa(Empresa empresa) { this.empresa = empresa; }
    public String getNombreChofer() { return nombreChofer; }
    public void setNombreChofer(String nombreChofer) { this.nombreChofer = nombreChofer; }
}