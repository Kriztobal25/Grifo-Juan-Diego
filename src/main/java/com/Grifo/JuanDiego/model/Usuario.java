package com.Grifo.JuanDiego.model;

import jakarta.persistence.*;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    private String username;
    private String passwordHash;

    @OneToOne
    @JoinColumn(name = "dni_empleado")
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "id_rol")
    private Roles rol;

    // Getters y Setters Manuales
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public Empleado getEmpleado() { return empleado; }
    public void setEmpleado(Empleado empleado) { this.empleado = empleado; }
    public Roles getRol() { return rol; }
    public void setRol(Roles rol) { this.rol = rol; }
}