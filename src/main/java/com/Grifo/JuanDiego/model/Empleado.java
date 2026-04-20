package com.Grifo.JuanDiego.model;

import jakarta.persistence.*;

@Entity
@Table(name = "empleado") // Asegura que apunte a la tabla en minúsculas
public class Empleado {

    @Id
    @Column(name = "dni_empleado", length = 8) // Mapeo exacto del snake_case de SQL
    private String dniEmpleado;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "telefono", length = 15)
    private String telefono;

    // Getters y Setters
    public String getDniEmpleado() { return dniEmpleado; }
    public void setDniEmpleado(String dniEmpleado) { this.dniEmpleado = dniEmpleado; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}