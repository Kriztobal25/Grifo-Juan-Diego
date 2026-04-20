package com.Grifo.JuanDiego.service;

import com.Grifo.JuanDiego.model.Pagos;
import com.Grifo.JuanDiego.model.Cobranza;
import com.Grifo.JuanDiego.repository.PagosRepository;
import com.Grifo.JuanDiego.repository.CobranzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.nio.file.*;
import java.util.UUID;

@Service
public class PagoService {

    @Autowired
    private PagosRepository pagosRepository;

    @Autowired
    private CobranzaRepository cobranzaRepository;

    // Carpeta donde se guardarán los vouchers físicamente
    private final String rootFolder = "uploads";

    @Transactional
    public Pagos registrarPago(Pagos pago, MultipartFile archivo) {
        // Generar N° Operación Automático
        pago.setNroOperacion("PAG-" + java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase());

        if (archivo != null && !archivo.isEmpty()) {
            try {
                java.nio.file.Files.createDirectories(java.nio.file.Paths.get("uploads"));
                String nombreArchivo = java.util.UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename();
                java.nio.file.Path path = java.nio.file.Paths.get("uploads/" + nombreArchivo);
                java.nio.file.Files.copy(archivo.getInputStream(), path, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                pago.setComprobante(nombreArchivo);
            } catch (Exception e) {
                throw new RuntimeException("Error con el voucher: " + e.getMessage());
            }
        }

        Cobranza cobranza = cobranzaRepository.findById(pago.getCobranza().getIdCobranza())
                .orElseThrow(() -> new RuntimeException("Registro de deuda no encontrado"));

        cobranza.setMontoPendiente(java.math.BigDecimal.ZERO);
        cobranza.setEstadoCobranza(Cobranza.EstadoCobranza.Pagado);

        cobranzaRepository.save(cobranza);
        return pagosRepository.save(pago);
    }
}