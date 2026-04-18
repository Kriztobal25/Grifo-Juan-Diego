package com.Grifo.JuanDiego.model;

import jakarta.persistence.*;

@Entity
public class Vehiculo {
    @Id
    private String placa;
    
    @ManyToOne
    @JoinColumn(name = "dni_cliente")
    private Cliente cliente;
    
    private String marcaModelo;

    // Getters y Setters
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public String getMarcaModelo() { return marcaModelo; }
    public void setMarcaModelo(String marcaModelo) { this.marcaModelo = marcaModelo; }
}