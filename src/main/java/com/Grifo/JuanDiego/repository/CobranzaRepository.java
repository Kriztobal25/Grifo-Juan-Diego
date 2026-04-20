package com.Grifo.JuanDiego.repository;

import com.Grifo.JuanDiego.model.Cobranza;
import com.Grifo.JuanDiego.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional; // IMPORTANTE

public interface CobranzaRepository extends JpaRepository<Cobranza, Long> {
    
    // Añade esta línea exactamente así:
    Optional<Cobranza> findByEmpresa(Empresa empresa);
    
}