package com.Grifo.JuanDiego.service;

import com.Grifo.JuanDiego.model.Cobranza;
import com.Grifo.JuanDiego.repository.CobranzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class CobranzaService {

    @Autowired
    private CobranzaRepository cobranzaRepository;

    // Listar todas las cobranzas para el panel administrativo
    public List<Cobranza> listarTodas() {
        return cobranzaRepository.findAll();
    }

    /**
     * Tarea Programada: Aplicar Mora del 2%
     * Se ejecuta el primer día de cada mes a las 00:00.
     * La expresión cron "0 0 0 1 * ?" significa: Segundo 0, Minuto 0, Hora 0, Día 1 de cada Mes.
     */
    @Scheduled(cron = "0 0 0 1 * ?")
    @Transactional
    public void aplicarMoraMensual() {
        // Filtrar solo las empresas con deuda vencida
        List<Cobranza> vencidas = cobranzaRepository.findAll().stream()
                .filter(c -> c.getEstadoCobranza() == Cobranza.EstadoCobranza.Vencido)
                .toList();

        for (Cobranza c : vencidas) {
            BigDecimal deudaActual = c.getMontoPendiente();
            
            // Calculamos el recargo del 2% (0.02)
            BigDecimal montoMora = deudaActual.multiply(new BigDecimal("0.02"));
            
            // Sumamos la mora al monto pendiente actual
            c.setMontoPendiente(deudaActual.add(montoMora));
            
            cobranzaRepository.save(c);
        }
        System.out.println(">>> Se ha aplicado el 2% de mora a las cuentas vencidas.");
    }

    // Método para cambiar el estado a Vencido manualmente o por fecha
    public void marcarComoVencido(Long id) {
        Cobranza c = cobranzaRepository.findById(id).orElseThrow();
        c.setEstadoCobranza(Cobranza.EstadoCobranza.Vencido);
        cobranzaRepository.save(c);
    }

    public void crearNuevaGestion(String ruc) {
        // Lógica básica para abrir un nuevo periodo
    }
}