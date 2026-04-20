package com.Grifo.JuanDiego.service;

import com.Grifo.JuanDiego.model.Roles;
import com.Grifo.JuanDiego.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class RolService {

    @Autowired
    private RolesRepository rolesRepository;

    @Transactional
    public Roles guardar(Roles rol) {
        // Validación: Convertir el nombre a Mayúsculas para mantener consistencia (ADMIN, VENDEDOR)
        if (rol.getNombreRol() != null) {
            rol.setNombreRol(rol.getNombreRol().toUpperCase().trim());
        }
        return rolesRepository.save(rol);
    }
    
    public List<Roles> listarRoles() {
        return rolesRepository.findAll();
    }

    public Roles buscarPorId(Integer id) {
        return rolesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + id));
    }
}