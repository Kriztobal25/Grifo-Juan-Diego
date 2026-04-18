package com.Grifo.JuanDiego.service;

import com.Grifo.JuanDiego.model.Roles;
import com.Grifo.JuanDiego.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RolService {
    @Autowired
    private RolesRepository rolesRepository;

    public Roles guardar(Roles rol) {
        return rolesRepository.save(rol);
    }
    
    public List<Roles> listarRoles() {
        return rolesRepository.findAll();
    }
}