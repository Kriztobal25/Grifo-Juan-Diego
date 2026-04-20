package com.Grifo.JuanDiego.service;

import com.Grifo.JuanDiego.model.Empleado;
import com.Grifo.JuanDiego.model.Usuario;
import com.Grifo.JuanDiego.repository.EmpleadoRepository;
import com.Grifo.JuanDiego.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importante para borrados
import java.util.List;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Empleado> listarTodos() {
        return empleadoRepository.findAll();
    }

    public void guardar(Empleado empleado) {
        empleadoRepository.save(empleado);
    }

    public Empleado buscarPorDni(String dni) {
        return empleadoRepository.findById(dni)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con DNI: " + dni));
    }

    /**
     * Versión completa de eliminación con validación de credenciales de administrador
     */
    @Transactional
    public void eliminarConSeguridad(String dniEmpleado, String passwordAdmin, String usernameAdmin) {
        // 1. Buscamos al administrador que intenta realizar la acción
        Usuario admin = usuarioRepository.findByUsername(usernameAdmin)
                .orElseThrow(() -> new RuntimeException("Administrador no encontrado"));

        // 2. Verificamos que sea ADMIN y que la contraseña coincida
        // Importante: passwordEncoder.matches funciona tanto para BCrypt como para NoOp
        if (passwordEncoder.matches(passwordAdmin, admin.getPasswordHash()) && 
            admin.getRol().getNombreRol().equalsIgnoreCase("ADMIN")) {
            
            // 3. Verificamos si existe el empleado antes de borrar
            if (empleadoRepository.existsById(dniEmpleado)) {
                empleadoRepository.deleteById(dniEmpleado);
            } else {
                throw new RuntimeException("El empleado con DNI " + dniEmpleado + " no existe.");
            }
        } else {
            throw new RuntimeException("Acceso denegado: Credenciales incorrectas o permisos insuficientes.");
        }
    }
}