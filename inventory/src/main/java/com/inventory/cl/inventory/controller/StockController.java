


package com.inventory.cl.inventory.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.cl.inventory.model.StockItem;
import com.inventory.cl.inventory.service.StockService;

@RestController
@RequestMapping("/api/inventory")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping
    public List<StockItem> obtenerTodos() {
        return stockService.obtenerTodos();
    }

    @GetMapping("/{productoId}")
    public Optional<StockItem> obtenerPorProductoId(@PathVariable Long productoId) {
        return stockService.buscarPorProductoId(productoId);
    }

    @PostMapping // endpoint para guardar stock desde cero o remplazar stock existente 
    public StockItem guardar(@RequestBody StockItem stockItem) {
        return stockService.guardar(stockItem);
    }

    @DeleteMapping("/{id}") // endpoint para eliminar stock, requiere el id del producto
    public void eliminar(@PathVariable Long id) {
        stockService.eliminar(id);
    }
    @PostMapping("/agregar-stock/{productoId}/{cantidad}") //endpoint para agregar stock, requiere el id del producto y la cantidad a agregar
    //http://localhost:8080/api/inventory/agregar-stock/101/25
    public Optional<StockItem> agregarStock(@PathVariable Long productoId, @PathVariable int cantidad) {
        return stockService.agregarStock(productoId, cantidad);
    }
    @PostMapping("/restar-stock/{productoId}/{cantidad}")//endpoint para restar stock, requiere el id del producto y la cantidad a restar 
    //quizas redundante porque el endpoint de agregar stock hace lo mismo si ocupamos negativo
    public Optional<StockItem> restarStock(@PathVariable Long productoId, @PathVariable int cantidad) {
        return stockService.restarStock(productoId, cantidad);
    }

}
