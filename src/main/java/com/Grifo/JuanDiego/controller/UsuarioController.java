package com.Grifo.JuanDiego.controller;

import com.Grifo.JuanDiego.model.Usuario;
import com.Grifo.JuanDiego.service.UsuarioService;
import com.Grifo.JuanDiego.service.EmpleadoService;
import com.Grifo.JuanDiego.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private RolService rolService;

    @GetMapping
    public String listar(Model model) {
        // VITAL: Estas 3 líneas evitan el Error 500
        model.addAttribute("usuarios", usuarioService.listarTodos());
        model.addAttribute("empleados", empleadoService.listarTodos());
        model.addAttribute("roles", rolService.listarRoles());
        
        // El objeto vacío para el formulario
        if (!model.containsAttribute("usuario")) {
            model.addAttribute("usuario", new Usuario());
        }
        
        return "usuarios/usuarios";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Usuario usuario, 
                          @RequestParam("passwordPlana") String passwordPlana, 
                          RedirectAttributes flash) {
        try {
            usuarioService.crearUsuario(usuario, passwordPlana);
            flash.addFlashAttribute("success", "Acceso vinculado correctamente.");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al vincular: " + e.getMessage());
        }
        return "redirect:/usuarios";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        try {
            usuarioService.eliminarUsuario(id);
            flash.addFlashAttribute("success", "Acceso revocado con éxito.");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "No se pudo eliminar: " + e.getMessage());
        }
        return "redirect:/usuarios";
    }
}