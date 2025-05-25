package com.marketplace.compra.Service;

import com.marketplace.compra.Model.Compra;
import com.marketplace.compra.Repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    public Compra crearCompra(Compra compra) {
        return compraRepository.save(compra);
    }

    public List<Compra> obtenerTodas() {
        return compraRepository.findAll();
    }

    public void eliminarCompra(Long id) {
    compraRepository.deleteById(id);
}


}
