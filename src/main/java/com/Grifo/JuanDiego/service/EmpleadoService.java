package com.Grifo.JuanDiego.service;

import com.Grifo.JuanDiego.model.Empleado;
import com.Grifo.JuanDiego.model.Usuario;
import com.Grifo.JuanDiego.repository.EmpleadoRepository;
import com.Grifo.JuanDiego.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoService {
    @Autowired
    private EmpleadoRepository empleadoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder; // Requiere Spring Security

    public void eliminarEmpleado(String dni, String passwordAdmin, String usernameAdmin) {
        Usuario admin = usuarioRepository.findByUsername(usernameAdmin).orElseThrow();
        
        // Verificamos contraseña y que sea Admin
        if (passwordEncoder.matches(passwordAdmin, admin.getPasswordHash()) && 
            admin.getRol().getNombreRol().equals("Admin")) {
            
            // Se elimina el empleado. Sus registros en Credito o Pagos 
            // no se borran por la integridad referencial de la BD (quedará el DNI como histórico)
            empleadoRepository.deleteById(dni);
        } else {
            throw new RuntimeException("No autorizado o contraseña incorrecta");
        }
    }

    public void guardar(Empleado empleado) {
        empleadoRepository.save(empleado);
    }

    public boolean eliminarConSeguridad(String dniEmpleado, String passwordAdmin, String usernameAdmin) {
        // Aquí puedes añadir la lógica de validación de admin después
        // Por ahora, para que compile:
        empleadoRepository.deleteById(dniEmpleado);
        return true;
    }
}