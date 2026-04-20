package com.Grifo.JuanDiego.repository;

import com.Grifo.JuanDiego.model.Pagos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PagosRepository extends JpaRepository<Pagos, Long> {
    // CORRECCIÓN: Quitamos el guion bajo
    List<Pagos> findByCobranzaIdCobranza(Long idCobranza);
}