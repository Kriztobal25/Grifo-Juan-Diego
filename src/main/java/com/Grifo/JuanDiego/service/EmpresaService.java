package com.Grifo.JuanDiego.service;

import com.Grifo.JuanDiego.model.Empresa;
import com.Grifo.JuanDiego.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpresaService {
    @Autowired
    private EmpresaRepository empresaRepository;

    public Empresa registrarOActualizar(Empresa empresa) {
        // La validación de RUC repetido la hace JPA por ser @Id al lanzar una excepción si intentas crear uno nuevo que ya existe
        return empresaRepository.save(empresa);
    }

    // Solo retornamos empresas activas para la interfaz de usuario
    public List<Empresa> listarVisibles() {
        return empresaRepository.findAll().stream()
                .filter(e -> e.getEstadoEmpresa() == Empresa.EstadoEmpresa.Activo)
                .collect(Collectors.toList());
    }

    public void suspenderEmpresa(String ruc) {
        Empresa e = empresaRepository.findById(ruc).orElseThrow();
        e.setEstadoEmpresa(Empresa.EstadoEmpresa.Suspendido);
        empresaRepository.save(e);
    }
}