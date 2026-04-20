package com.Grifo.JuanDiego.service;

import com.Grifo.JuanDiego.model.Empresa;
import com.Grifo.JuanDiego.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    /**
     * Registra o actualiza una empresa.
     * Al usar el RUC como @Id, si envías un RUC existente, JPA actualizará el registro.
     */
    @Transactional
    public Empresa registrarOActualizar(Empresa empresa) {
        if (empresa.getRuc() == null || empresa.getRuc().length() != 11) {
            throw new RuntimeException("El RUC debe tener exactamente 11 dígitos.");
        }

        // BUSCAMOS SI YA EXISTE PARA NO PERDER EL ESTADO
        return empresaRepository.findById(empresa.getRuc())
            .map(empresaExistente -> {
                empresaExistente.setRazonSocial(empresa.getRazonSocial());
                empresaExistente.setLimiteCreditoGalones(empresa.getLimiteCreditoGalones());
                // IMPORTANTE: No tocamos el estado, se queda como estaba (Activo o Suspendido)
                return empresaRepository.save(empresaExistente);
            })
            .orElseGet(() -> {
                // Si es nueva, por defecto entra como Activo (o el default que tengas)
                return empresaRepository.save(empresa);
            });
    }

    /**
     * Retorna solo las empresas con estado 'Activo'.
     * Útil para llenar los <select> en los formularios de Clientes y Créditos.
     */
    public List<Empresa> listarVisibles() {
        return empresaRepository.findAll().stream()
                .filter(e -> e.getEstadoEmpresa() == Empresa.EstadoEmpresa.Activo)
                .collect(Collectors.toList());
    }

    public Empresa buscarPorRuc(String ruc) {
        return empresaRepository.findById(ruc).orElse(null);
    }

    /**
     * Cambia el estado de una empresa a 'Suspendido'.
     */
    @Transactional
    public void suspenderEmpresa(String ruc) {
        Empresa e = empresaRepository.findById(ruc)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada con RUC: " + ruc));
        
        e.setEstadoEmpresa(Empresa.EstadoEmpresa.Suspendido);
        empresaRepository.save(e);
    }
    
    // Método adicional para el mantenimiento general de empresas
    public List<Empresa> listarTodas() {
        return empresaRepository.findAll();
    }
}