package com.Grifo.JuanDiego.repository;

import com.Grifo.JuanDiego.model.Credito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CreditoRepository extends JpaRepository<Credito, Long> {
    List<Credito> findByVehiculo_Placa(String placa);
}