package com.Grifo.JuanDiego.service;

import com.Grifo.JuanDiego.model.Usuario;
import com.Grifo.JuanDiego.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public void crearUsuario(Usuario usuario, String passwordPlana) {
        
        if (usuario.getEmpleado() == null) throw new RuntimeException("Seleccione un empleado");
        
        usuario.setPasswordHash(passwordPlana); 
        
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("El usuario no existe.");
        }
        usuarioRepository.deleteById(id);
    }
}