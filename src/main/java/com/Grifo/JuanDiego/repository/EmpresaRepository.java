package com.Grifo.JuanDiego.repository;

import com.Grifo.JuanDiego.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, String> {
    
}