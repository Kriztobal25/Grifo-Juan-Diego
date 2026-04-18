package com.Grifo.JuanDiego.service;

import com.Grifo.JuanDiego.model.Vehiculo;
import com.Grifo.JuanDiego.model.Empresa;
import com.Grifo.JuanDiego.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehiculoService {
    @Autowired
    private VehiculoRepository vehiculoRepository;

    public Vehiculo registrarOActualizarVehiculo(Vehiculo nuevoVehiculo) {
        return vehiculoRepository.findById(nuevoVehiculo.getPlaca())
            .map(vExistente -> {
                // Regla: Permitir re-registro solo si la empresa del dueño anterior está suspendida
                if (vExistente.getCliente().getEmpresa().getEstadoEmpresa() == Empresa.EstadoEmpresa.Suspendido) {
                    return vehiculoRepository.save(nuevoVehiculo);
                }
                throw new RuntimeException("La placa ya está vinculada a una empresa activa.");
            }).orElseGet(() -> vehiculoRepository.save(nuevoVehiculo));
    }
}