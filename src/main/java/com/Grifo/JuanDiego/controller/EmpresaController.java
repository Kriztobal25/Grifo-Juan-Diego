package com.Grifo.JuanDiego.controller;

import com.Grifo.JuanDiego.model.Empresa;
import com.Grifo.JuanDiego.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/empresas") // Esta es la ruta que pones en el navegador
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("empresas", empresaService.listarTodas());
        // Agregamos esto para que el formulario th:object no sea null
        if (!model.containsAttribute("empresa")) {
            model.addAttribute("empresa", new Empresa());
        }
        return "empresas/empresas"; 
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Empresa empresa, RedirectAttributes flash) {
        try {
            empresaService.registrarOActualizar(empresa);
            flash.addFlashAttribute("success", "Empresa guardada con éxito.");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al guardar: " + e.getMessage());
        }
        return "redirect:/empresas";
    }

    @GetMapping("/editar/{ruc}")
    public String editar(@PathVariable String ruc, Model model) {
        Empresa empresa = empresaService.buscarPorRuc(ruc);
        if (empresa == null) {
            return "redirect:/empresas";
        }
        model.addAttribute("empresa", empresa); // Para que el formulario se llene
        model.addAttribute("empresas", empresaService.listarTodas()); // Para mantener la tabla cargada
        return "empresas/empresas";
    }

    @PostMapping("/suspender/{ruc}")
    public String suspender(@PathVariable String ruc, RedirectAttributes flash) {
        try {
            empresaService.suspenderEmpresa(ruc);
            flash.addFlashAttribute("success", "Estado de la empresa actualizado.");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/empresas";
    }
}