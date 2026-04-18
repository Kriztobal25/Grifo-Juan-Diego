package com.Grifo.JuanDiego.service;

import com.Grifo.JuanDiego.model.Cliente;
import com.Grifo.JuanDiego.model.Empresa;
import com.Grifo.JuanDiego.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente registrarCliente(Cliente nuevoCliente) {
        return clienteRepository.findById(nuevoCliente.getDniCliente())
            .map(clienteExistente -> {
                // Si existe, solo permitimos re-registro si su empresa actual está suspendida
                if (clienteExistente.getEmpresa().getEstadoEmpresa() == Empresa.EstadoEmpresa.Suspendido) {
                    return clienteRepository.save(nuevoCliente);
                }
                throw new RuntimeException("El DNI ya está vinculado a una empresa activa.");
            }).orElseGet(() -> clienteRepository.save(nuevoCliente));
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }
}