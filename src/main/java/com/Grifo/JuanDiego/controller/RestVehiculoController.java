package com.Grifo.JuanDiego.controller;

import com.Grifo.JuanDiego.model.Vehiculo;
import com.Grifo.JuanDiego.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
public class RestVehiculoController {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @GetMapping("/empresa/{ruc}")
    public List<Vehiculo> listarPorEmpresa(@PathVariable String ruc) {
        // Ahora sí encontrará el método
        return vehiculoRepository.findByClienteEmpresaRuc(ruc);
    }
}