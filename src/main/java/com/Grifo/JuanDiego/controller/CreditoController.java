package com.Grifo.JuanDiego.controller;

import com.Grifo.JuanDiego.model.*;
import com.Grifo.JuanDiego.service.*;
import com.Grifo.JuanDiego.repository.EmpleadoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.ArrayList;

@Controller
@RequestMapping("/creditos")
public class CreditoController {

    @Autowired
    private CreditoService creditoService;
    
    @Autowired
    private CatalogoService catalogoService;
    
    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @GetMapping("/despacho")
    public String formularioDespacho(Model model, HttpSession session) {
        // 1. Inicializar objeto para el formulario si no viene de un error
        if (!model.containsAttribute("credito")) {
            Credito credito = new Credito();
            credito.setVehiculo(new Vehiculo());
            credito.setCombustible(new Catalogo_Combustible());
            model.addAttribute("credito", credito);
        }

        // 2. Cargar datos para los combos
        model.addAttribute("empresas", empresaService.listarTodas());
        model.addAttribute("combustibles", catalogoService.listarTodos());
        
        // 3. Inicializar lista de vehículos vacía (se llena por JS en el HTML)
        model.addAttribute("vehiculos", new ArrayList<Vehiculo>());
        
        // 4. Nombre del operador para la interfaz
        String operario = (String) session.getAttribute("usuarioNombre");
        model.addAttribute("operadorNombre", operario != null ? operario : "Operador del Sistema");
        
        return "creditos/despacho-credito"; 
    }

    @PostMapping("/guardar")
    public String registrarDespacho(@ModelAttribute("credito") Credito credito, 
                                    RedirectAttributes flash, 
                                    HttpSession session) {
        try {
            // 1. Obtener DNI de la sesión
            String dni = (String) session.getAttribute("usuarioDni");
            Empleado empleadoParaRegistro;

            if (dni != null) {
                // Si hay sesión, usamos ese empleado
                empleadoParaRegistro = empleadoRepository.findById(dni)
                    .orElseThrow(() -> new RuntimeException("Empleado de sesión no encontrado."));
            } else {
                // 2. SI LA SESIÓN EXPIRÓ: Jalamos el primer empleado de la BD
                List<Empleado> empleados = empleadoRepository.findAll();
                if (empleados.isEmpty()) {
                    throw new RuntimeException("No existen empleados en la base de datos para asignar el despacho.");
                }
                empleadoParaRegistro = empleados.get(0);
                System.out.println("DEBUG: Sesión expirada. Asignando por defecto a: " + empleadoParaRegistro.getNombre());
            }

            // 3. Asignar el empleado recuperado al crédito
            credito.setEmpleado(empleadoParaRegistro);

            // 4. Ejecutar el registro en el Service (Cálculos y Cobranza)
            creditoService.registrarDespacho(credito);
            
            flash.addFlashAttribute("success", "¡Despacho registrado con éxito! Responsable: " + empleadoParaRegistro.getNombre());
            
        } catch (Exception e) {
            e.printStackTrace();
            flash.addFlashAttribute("error", "Error al registrar: " + e.getMessage());
            // Devolvemos el objeto para no perder lo que el usuario ya escribió
            flash.addFlashAttribute("credito", credito); 
        }
        return "redirect:/creditos/despacho";
    }
}