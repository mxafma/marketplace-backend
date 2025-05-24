package com.inventory.cl.inventory.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventory.cl.inventory.model.StockItem;
import com.inventory.cl.inventory.repository.StockItemRepository;

@Service
public class StockService {

    @Autowired
    private StockItemRepository repository;

    public List<StockItem> obtenerTodos() { // metodo para obtener todos los stocks
        return repository.findAll();
    }

    public Optional<StockItem> buscarPorProductoId(Long productoId) { // este método busca un stock por id
        return repository.findByProductoId(productoId);
    }

    public StockItem guardar(StockItem stockItem) { // este método guarda un stock nuevo
        return repository.save(stockItem);
    }

    public void eliminar(Long id) { // este método elimina un stock
        repository.deleteById(id);
    }
    public Optional<StockItem> agregarStock(Long productoId, int cantidad) { // este método agrega stock
        Optional<StockItem> existente = repository.findByProductoId(productoId);
        if (existente.isPresent()) {
            StockItem item = existente.get();
            item.setCantidadDisponible(item.getCantidadDisponible() + cantidad);
            return Optional.of(repository.save(item));
        }
        return Optional.empty();
    }

    public Optional<StockItem> restarStock(Long productoId, int cantidad) { // este método resta stock
        Optional<StockItem> existente = repository.findByProductoId(productoId);
        if (existente.isPresent()) {
            StockItem item = existente.get();
            int nuevaCantidad = item.getCantidadDisponible() - cantidad;
            if (nuevaCantidad >= 0) {
                item.setCantidadDisponible(nuevaCantidad);
                return Optional.of(repository.save(item));
            }
        }
        return Optional.empty();
    }
}



