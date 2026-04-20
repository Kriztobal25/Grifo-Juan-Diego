package com.Grifo.JuanDiego.service;

import com.Grifo.JuanDiego.model.Catalogo_Combustible;
import com.Grifo.JuanDiego.repository.CatalogoCombustibleRepository; // <--- Nombre corregido
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class CatalogoService {

    @Autowired
    private CatalogoCombustibleRepository repository; // <--- Nombre corregido

    public List<Catalogo_Combustible> listarTodos() {
        return repository.findAll();
    }

    public Catalogo_Combustible buscarPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public void actualizarPrecio(Integer id, BigDecimal nuevoPrecio) {
        Catalogo_Combustible c = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Combustible no encontrado con ID: " + id));
        
        c.setPrecioVenta(nuevoPrecio);
        repository.save(c);
    }

    public void registrarNuevo(Catalogo_Combustible combustible) {
        if (combustible.getPrecioVenta() != null && combustible.getPrecioVenta().compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("El precio no puede ser negativo");
        }
        repository.save(combustible);
    }
}