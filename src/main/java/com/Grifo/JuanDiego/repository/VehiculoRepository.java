package com.Grifo.JuanDiego.repository;

import com.Grifo.JuanDiego.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, String> {

    // Este es el método que le falta al compilador:
    // Navega: Vehiculo -> Cliente -> Empresa -> Ruc
    List<Vehiculo> findByClienteEmpresaRuc(String ruc);
}