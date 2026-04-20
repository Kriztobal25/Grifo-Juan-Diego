package com.Grifo.JuanDiego.service;

import com.Grifo.JuanDiego.model.Cobranza;
import com.Grifo.JuanDiego.repository.CobranzaRepository;
import com.Grifo.JuanDiego.repository.EmpresaRepository; // Necesario para crearNuevaGestion
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode; // Importante para cálculos financieros
import java.util.List;

@Service
public class CobranzaService {

    @Autowired
    private CobranzaRepository cobranzaRepository;
    
    @Autowired
    private EmpresaRepository empresaRepository;

    public List<Cobranza> listarTodas() {
        return cobranzaRepository.findAll();
    }

    /**
     * Tarea Programada: Aplicar Mora del 2%
     * Se ejecuta el primer día de cada mes a las 00:00.
     */
    @Scheduled(cron = "0 0 0 1 * ?")
    @Transactional
    public void aplicarMoraMensual() {
        // Mejoramos la eficiencia filtrando directamente si tienes el método en el Repo
        // O manteniendo el stream pero asegurando el manejo de nulos
        List<Cobranza> vencidas = cobranzaRepository.findAll().stream()
                .filter(c -> c.getEstadoCobranza() == Cobranza.EstadoCobranza.Vencido)
                .toList();

        for (Cobranza c : vencidas) {
            BigDecimal deudaActual = (c.getMontoPendiente() != null) ? c.getMontoPendiente() : BigDecimal.ZERO;
            
            if (deudaActual.compareTo(BigDecimal.ZERO) > 0) {
                // Calculamos mora con escala de 2 decimales para evitar errores de redondeo en soles
                BigDecimal montoMora = deudaActual.multiply(new BigDecimal("0.02"))
                                                  .setScale(2, RoundingMode.HALF_UP);
                
                c.setMontoPendiente(deudaActual.add(montoMora));
                cobranzaRepository.save(c);
            }
        }
        System.out.println(">>> [CRON] Se ha aplicado el 2% de mora a las cuentas vencidas.");
    }

    public BigDecimal calcularDeudaTotal() {
        BigDecimal total = cobranzaRepository.findAll().stream()
                .map(c -> c.getMontoPendiente() != null ? c.getMontoPendiente() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return total == null ? BigDecimal.ZERO : total;
    }

    public long contarEmpresasEnMora() {
        return cobranzaRepository.findAll().stream()
                .filter(c -> c.getEstadoCobranza() == Cobranza.EstadoCobranza.Vencido)
                .count();
    }

    @Transactional
    public void marcarComoVencido(Long id) {
        Cobranza c = cobranzaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cobranza no encontrada con ID: " + id));
        c.setEstadoCobranza(Cobranza.EstadoCobranza.Vencido);
        cobranzaRepository.save(c);
    }

    @Transactional
    public void crearNuevaGestion(String ruc) {
        // Implementación sugerida para que el Controller funcione:
        Cobranza nueva = new Cobranza();
        nueva.setEmpresa(empresaRepository.findById(ruc)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada")));
        nueva.setMontoPendiente(BigDecimal.ZERO);
        nueva.setEstadoCobranza(Cobranza.EstadoCobranza.Pendiente);
        cobranzaRepository.save(nueva);
    }
}