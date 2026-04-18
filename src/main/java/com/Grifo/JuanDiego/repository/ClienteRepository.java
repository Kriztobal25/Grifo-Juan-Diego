package com.Grifo.JuanDiego.repository;

import com.Grifo.JuanDiego.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {
    List<Cliente> findByEmpresa_Ruc(String ruc);
}