package com.Grifo.JuanDiego.controller;

import com.Grifo.JuanDiego.model.Cliente;
import com.Grifo.JuanDiego.service.ClienteService;
import com.Grifo.JuanDiego.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private EmpresaService empresaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("empresasActivas", empresaService.listarVisibles());
        return "clientes/lista";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Cliente cliente, RedirectAttributes flash) {
        try {
            clienteService.registrarCliente(cliente);
            flash.addFlashAttribute("success", "Cliente registrado/actualizado correctamente.");
        } catch (RuntimeException e) {
            flash.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/clientes";
    }
}