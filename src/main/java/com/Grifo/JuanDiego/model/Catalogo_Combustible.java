package com.Grifo.JuanDiego.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "catalogo_combustible")
public class Catalogo_Combustible {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_combustible") 
    private Integer id;

    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @Column(name = "precio_venta", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioVenta;

    // Constructor vacío obligatorio para JPA
    public Catalogo_Combustible() {
    }

    // Constructor para facilitar pruebas
    public Catalogo_Combustible(String nombre, BigDecimal precioVenta) {
        this.nombre = nombre;
        this.precioVenta = precioVenta;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }
}