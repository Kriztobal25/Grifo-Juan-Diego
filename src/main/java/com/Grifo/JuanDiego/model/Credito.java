package com.Grifo.JuanDiego.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDate;

@Entity
@Table(name = "credito")
public class Credito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_credito")
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

    @Column(name = "cantidad_galones", precision = 10, scale = 3)
    private BigDecimal cantidadGalones;

    // CORRECCIÓN: Coincide con tu SQL "precio_aplicado"
    @Column(name = "precio_aplicado", precision = 10, scale = 2) 
    private BigDecimal precioAplicado;

    @Column(name = "total_deuda", precision = 10, scale = 2)
    private BigDecimal totalDeuda;

    // Generado automáticamente por el sistema
    @Column(name = "fecha_inicio", updatable = false)
    private LocalDateTime fechaInicio = LocalDateTime.now();

    // Recibido desde el <input type="date"> del HTML
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    public Credito() {}

    // --- Getters y Setters ---

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

    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
}