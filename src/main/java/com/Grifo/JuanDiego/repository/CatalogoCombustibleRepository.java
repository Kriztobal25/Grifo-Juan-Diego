package com.Grifo.JuanDiego.repository;

import com.Grifo.JuanDiego.model.Catalogo_Combustible;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogoCombustibleRepository extends JpaRepository<Catalogo_Combustible, Integer> {
    
}