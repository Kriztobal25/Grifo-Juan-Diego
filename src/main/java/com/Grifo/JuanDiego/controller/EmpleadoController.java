package com.Grifo.JuanDiego.controller;

import com.Grifo.JuanDiego.model.Empleado;
import com.Grifo.JuanDiego.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping
    public String listar(Model model) {
        try {
            // Mandamos la lista real
            model.addAttribute("empleados", empleadoService.listarTodos());
        } catch (Exception e) {
            // Si algo falla en la DB, mandamos una lista vacía para que el HTML no explote
            model.addAttribute("empleados", new ArrayList<Empleado>());
            System.out.println("ERROR CARGANDO EMPLEADOS: " + e.getMessage());
        }
        return "empleados/empleados"; 
    }

    // Este método es para que el botón Editar funcione sin recargar página
    @GetMapping("/buscar/{dni}")
    @ResponseBody
    public Empleado buscarParaEditar(@PathVariable("dni") String dni) {
        return empleadoService.buscarPorDni(dni);
    }
    
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Empleado empleado) {
        empleadoService.guardar(empleado);
        return "redirect:/empleados";
    }
}