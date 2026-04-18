package com.Grifo.JuanDiego.model;

import jakarta.persistence.*;

@Entity
public class Empleado {
    @Id
    private String dniEmpleado;

    @Column(nullable = false)
    private String nombre;

    private String telefono;

    // Getters y Setters
    public String getDniEmpleado() { return dniEmpleado; }
    public void setDniEmpleado(String dniEmpleado) { this.dniEmpleado = dniEmpleado; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}