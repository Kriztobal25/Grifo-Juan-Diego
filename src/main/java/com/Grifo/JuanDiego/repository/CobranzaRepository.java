package com.Grifo.JuanDiego.repository;

import com.Grifo.JuanDiego.model.Cobranza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CobranzaRepository extends JpaRepository<Cobranza, Long> {
    Optional<Cobranza> findByEmpresa_Ruc(String ruc);
}