package com.Grifo.JuanDiego.service;

import com.Grifo.JuanDiego.model.Pagos;
import com.Grifo.JuanDiego.model.Cobranza;
import com.Grifo.JuanDiego.repository.PagosRepository;
import com.Grifo.JuanDiego.repository.CobranzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
public class PagoService {

    @Autowired
    private PagosRepository pagosRepository;

    @Autowired
    private CobranzaRepository cobranzaRepository;

    /**
     * Registra un pago y actualiza la deuda de la empresa.
     * @Transactional asegura que si falla el descuento del dinero, no se guarde el registro del pago.
     */
    @Transactional
    public Pagos registrarPago(Pagos pago) {
        // 1. Guardamos el registro del pago (aquí ya viene con la ruta de la imagen del voucher)
        Pagos pagoGuardado = pagosRepository.save(pago);

        // 2. Buscamos la cobranza asociada a la empresa del pago
        Cobranza cobranza = cobranzaRepository.findById(pago.getCobranza().getIdCobranza())
                .orElseThrow(() -> new RuntimeException("No se encontró el registro de cobranza"));

        // 3. Lógica de descuento: Monto Pendiente = Pendiente - Pagado
        BigDecimal saldoAnterior = cobranza.getMontoPendiente();
        BigDecimal montoPagado = pago.getMontoPagado();
        BigDecimal nuevoSaldo = saldoAnterior.subtract(montoPagado);

        // 4. Validamos que el saldo no sea negativo (por si pagan de más por error)
        if (nuevoSaldo.compareTo(BigDecimal.ZERO) <= 0) {
            cobranza.setMontoPendiente(BigDecimal.ZERO);
            cobranza.setEstadoCobranza(Cobranza.EstadoCobranza.Pagado); // Cambia estado automáticamente
        } else {
            cobranza.setMontoPendiente(nuevoSaldo);
            // Si el estado era 'Vencido', se mantiene así hasta que salde el 100% o el admin lo cambie
        }

        // 5. Guardamos la cobranza actualizada
        cobranzaRepository.save(cobranza);

        return pagoGuardado;
    }
}