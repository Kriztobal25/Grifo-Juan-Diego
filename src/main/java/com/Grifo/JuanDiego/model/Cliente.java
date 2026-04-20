package com.Grifo.JuanDiego.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @Column(name = "dni_cliente", length = 8) // Coincide con tu PK en MySQL
    private String dniCliente;
    
    @ManyToOne
    @JoinColumn(name = "ruc_empresa") // Este ya estaba bien
    private Empresa empresa;
    
    @Column(name = "nombre_chofer", length = 100) // Mapeo para evitar error de columna inexistente
    private String nombreChofer;

    // Getters y Setters
    public String getDniCliente() { return dniCliente; }
    public void setDniCliente(String dniCliente) { this.dniCliente = dniCliente; }
    
    public Empresa getEmpresa() { return empresa; }
    public void setEmpresa(Empresa empresa) { this.empresa = empresa; }
    
    public String getNombreChofer() { return nombreChofer; }
    public void setNombreChofer(String nombreChofer) { this.nombreChofer = nombreChofer; }
}