package com.Grifo.JuanDiego.service;

import com.Grifo.JuanDiego.model.Usuario;
import com.Grifo.JuanDiego.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario crearUsuario(Usuario usuario, String passwordPlana) {
        // Encriptamos la contraseña antes de guardar
        usuario.setPasswordHash(passwordEncoder.encode(passwordPlana));
        return usuarioRepository.save(usuario);
    }

    public void actualizarPassword(Long idUsuario, String nuevaPassword) {
        Usuario user = usuarioRepository.findById(idUsuario).orElseThrow();
        user.setPasswordHash(passwordEncoder.encode(nuevaPassword));
        usuarioRepository.save(user);
    }
}