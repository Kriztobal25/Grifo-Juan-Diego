package com.Grifo.JuanDiego.model;

import jakarta.persistence.*;

@Entity
@Table(name = "vehiculo")
public class Vehiculo {

    @Id
    @Column(name = "placa", length = 7) // En Perú las placas suelen ser de 6 o 7 caracteres
    private String placa;
    
    @ManyToOne
    @JoinColumn(name = "dni_cliente")
    private Cliente cliente;
    
    @Column(name = "marca_modelo", length = 100) // Mapeo vital para evitar el error de columna inexistente
    private String marcaModelo;

    // Getters y Setters
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    
    public String getMarcaModelo() { return marcaModelo; }
    public void setMarcaModelo(String marcaModelo) { this.marcaModelo = marcaModelo; }
}