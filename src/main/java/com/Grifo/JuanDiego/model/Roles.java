package com.Grifo.JuanDiego.model;

import jakarta.persistence.*;

@Entity
@Table(name = "roles") // Especificamos el nombre de la tabla en minúsculas
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol") // Mapeo exacto del snake_case de tu SQL
    private Integer idRol;

    @Column(name = "nombre_rol", unique = true, nullable = false, length = 50)
    private String nombreRol;

    // Getters y Setters
    public Integer getIdRol() { return idRol; }
    public void setIdRol(Integer idRol) { this.idRol = idRol; }
    
    public String getNombreRol() { return nombreRol; }
    public void setNombreRol(String nombreRol) { this.nombreRol = nombreRol; }
}