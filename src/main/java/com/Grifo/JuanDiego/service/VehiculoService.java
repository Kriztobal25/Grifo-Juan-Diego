package com.Grifo.JuanDiego.service;

import com.Grifo.JuanDiego.model.Vehiculo;
import com.Grifo.JuanDiego.model.Cliente;
import com.Grifo.JuanDiego.repository.VehiculoRepository;
import com.Grifo.JuanDiego.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Vehiculo> listarTodos() {
        return vehiculoRepository.findAll();
    }

    // Para buscar el vehículo al presionar el botón Editar
    public Vehiculo buscarPorPlaca(String placa) {
        return vehiculoRepository.findById(placa).orElse(null);
    }

    @Transactional
    public Vehiculo registrarOActualizarVehiculo(Vehiculo nuevoVehiculo) {
        if (nuevoVehiculo.getPlaca() != null) {
            nuevoVehiculo.setPlaca(nuevoVehiculo.getPlaca().toUpperCase().trim());
        }

        // Lógica simplificada: Si existe actualiza, si no, crea.
        return vehiculoRepository.findById(nuevoVehiculo.getPlaca())
            .map(vExistente -> {
                vExistente.setCliente(nuevoVehiculo.getCliente());
                vExistente.setMarcaModelo(nuevoVehiculo.getMarcaModelo());
                return vehiculoRepository.save(vExistente);
            })
            .orElseGet(() -> vehiculoRepository.save(nuevoVehiculo));
    }
    
    @Transactional
    public void eliminarVehiculo(String placa) {
        vehiculoRepository.deleteById(placa);
    }
}