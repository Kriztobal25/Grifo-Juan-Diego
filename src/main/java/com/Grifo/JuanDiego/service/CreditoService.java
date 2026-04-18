package com.Grifo.JuanDiego.service;

import com.Grifo.JuanDiego.model.Credito;
import com.Grifo.JuanDiego.repository.CreditoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CreditoService {
    @Autowired
    private CreditoRepository repository;

    public List<Credito> listarTodos() {
        return repository.findAll();
    }

    public void registrarDespacho(Credito credito) {
        repository.save(credito);
    }
}