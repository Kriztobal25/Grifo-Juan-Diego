package com.Grifo.JuanDiego.service;

import com.Grifo.JuanDiego.model.Catalogo_Combustible;
import com.Grifo.JuanDiego.repository.CatalogoCombustibleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class CatalogoService {

    @Autowired
    private CatalogoCombustibleRepository repository;

    // Método para listar (lo necesitarán tus controladores)
    public List<Catalogo_Combustible> listarTodos() {
        return repository.findAll();
    }

    // Tu método de actualización con el fix de importación
    public void actualizarPrecio(Integer id, BigDecimal nuevoPrecio) {
        Catalogo_Combustible c = repository.findById(id).orElseThrow();
        c.setPrecioVenta(nuevoPrecio); 
        repository.save(c);
    }

    // Método para registrar nuevos tipos de combustible
    public void registrarNuevo(Catalogo_Combustible combustible) {
        repository.save(combustible);
    }
}