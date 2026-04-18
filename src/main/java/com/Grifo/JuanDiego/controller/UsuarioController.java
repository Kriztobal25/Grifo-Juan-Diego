package com.Grifo.JuanDiego.controller;

import com.Grifo.JuanDiego.model.Usuario;
import com.Grifo.JuanDiego.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/crear")
    public String crearUsuario(@ModelAttribute Usuario usuario, @RequestParam String password, RedirectAttributes flash) {
        usuarioService.crearUsuario(usuario, password);
        flash.addFlashAttribute("success", "Usuario creado exitosamente.");
        return "redirect:/configuracion";
    }

    @PostMapping("/cambiar-password")
    public String cambiarPass(@RequestParam Long idUsuario, @RequestParam String nuevaPassword, RedirectAttributes flash) {
        usuarioService.actualizarPassword(idUsuario, nuevaPassword);
        flash.addFlashAttribute("success", "Contraseña actualizada correctamente.");
        return "redirect:/perfil";
    }
}