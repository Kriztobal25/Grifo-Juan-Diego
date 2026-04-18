package com.Grifo.JuanDiego.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Credito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCredito;

    @ManyToOne
    @JoinColumn(name = "dni_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "placa")
    private Vehiculo vehiculo;

    @ManyToOne
    @JoinColumn(name = "id_combustible")
    private Catalogo_Combustible combustible;

    @ManyToOne
    @JoinColumn(name = "dni_empleado")
    private Empleado empleado;

    private BigDecimal cantidadGalones;
    private BigDecimal precioAplicado;
    private BigDecimal totalDeuda;
    private LocalDateTime fechaInicio = LocalDateTime.now();
    private LocalDateTime fechaVencimiento;

    // Getters y Setters
    public Long getIdCredito() { return idCredito; }
    public void setIdCredito(Long idCredito) { this.idCredito = idCredito; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Vehiculo getVehiculo() { return vehiculo; }
    public void setVehiculo(Vehiculo vehiculo) { this.vehiculo = vehiculo; }
    public Catalogo_Combustible getCombustible() { return combustible; }
    public void setCombustible(Catalogo_Combustible combustible) { this.combustible = combustible; }
    public Empleado getEmpleado() { return empleado; }
    public void setEmpleado(Empleado empleado) { this.empleado = empleado; }
    public BigDecimal getCantidadGalones() { return cantidadGalones; }
    public void setCantidadGalones(BigDecimal cantidadGalones) { this.cantidadGalones = cantidadGalones; }
    public BigDecimal getPrecioAplicado() { return precioAplicado; }
    public void setPrecioAplicado(BigDecimal precioAplicado) { this.precioAplicado = precioAplicado; }
    public BigDecimal getTotalDeuda() { return totalDeuda; }
    public void setTotalDeuda(BigDecimal totalDeuda) { this.totalDeuda = totalDeuda; }
    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDateTime fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDateTime getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDateTime fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
}