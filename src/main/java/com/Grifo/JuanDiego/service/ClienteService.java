package com.Grifo.JuanDiego.service;

import com.Grifo.JuanDiego.model.Cliente;
import com.Grifo.JuanDiego.model.Empresa;
import com.Grifo.JuanDiego.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Agregado para integridad
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public Cliente registrarCliente(Cliente nuevoCliente) {
        return clienteRepository.findById(nuevoCliente.getDniCliente())
            .map(clienteExistente -> {
                // Validación de negocio:
                // Si el cliente ya existe, solo permitimos actualizar sus datos o empresa
                // si la empresa actual está Suspendida.
                if (clienteExistente.getEmpresa() != null && 
                    clienteExistente.getEmpresa().getEstadoEmpresa() == Empresa.EstadoEmpresa.Suspendido) {
                    
                    // Actualizamos los datos del cliente existente con los nuevos
                    clienteExistente.setNombreChofer(nuevoCliente.getNombreChofer());
                    clienteExistente.setEmpresa(nuevoCliente.getEmpresa());
                    return clienteRepository.save(clienteExistente);
                }
                
                throw new RuntimeException("El DNI " + nuevoCliente.getDniCliente() + " ya está vinculado a una empresa activa.");
            })
            .orElseGet(() -> {
                // Validación básica de DNI para Perú
                if (nuevoCliente.getDniCliente() == null || nuevoCliente.getDniCliente().length() != 8) {
                    throw new RuntimeException("El DNI debe tener exactamente 8 caracteres.");
                }
                return clienteRepository.save(nuevoCliente);
            });
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }
}