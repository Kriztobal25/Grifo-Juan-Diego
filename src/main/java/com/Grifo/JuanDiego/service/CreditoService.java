package com.Grifo.JuanDiego.service;

import com.Grifo.JuanDiego.model.*;
import com.Grifo.JuanDiego.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CreditoService {

    @Autowired
    private CreditoRepository creditoRepository;

    @Autowired
    private CobranzaRepository cobranzaRepository;

    // CORRECCIÓN: Nombre exacto de tu interfaz sin el guion bajo
    @Autowired
    private CatalogoCombustibleRepository catalogoRepository; 

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    public List<Credito> listarTodos() {
        return creditoRepository.findAll();
    }

    @Transactional
    public Credito registrarDespacho(Credito credito) {
        // 1. Obtener combustible usando getId() porque así se llama en tu modelo
        Catalogo_Combustible combustible = catalogoRepository.findById(credito.getCombustible().getId())
            .orElseThrow(() -> new RuntimeException("Combustible no encontrado"));

        // Usamos getPrecioVenta() que es el getter correcto en tu modelo
        credito.setPrecioAplicado(combustible.getPrecioVenta());

        // 2. Calcular Total
        BigDecimal total = combustible.getPrecioVenta().multiply(credito.getCantidadGalones());
        credito.setTotalDeuda(total);

        // 3. Vincular Chofer por Placa (Para evitar el NULL en la BD)
        Vehiculo v = vehiculoRepository.findById(credito.getVehiculo().getPlaca())
            .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));
        credito.setCliente(v.getCliente());

        // 4. Guardar crédito
        Credito guardado = creditoRepository.save(credito);

        // 5. Actualizar Cobranza de la Empresa
        if (v.getCliente() != null && v.getCliente().getEmpresa() != null) {
            actualizarCobranza(v.getCliente().getEmpresa(), total);
        }

        return guardado;
    }

    private void actualizarCobranza(Empresa empresa, BigDecimal monto) {
        Cobranza cobranza = cobranzaRepository.findByEmpresa(empresa)
            .orElseGet(() -> new Cobranza(empresa, BigDecimal.ZERO, Cobranza.EstadoCobranza.Pendiente));
        
        cobranza.setMontoPendiente(cobranza.getMontoPendiente().add(monto));
        cobranzaRepository.save(cobranza);
    }
}