
package com.marketplace.compra.Controller;

import com.marketplace.compra.Model.Compra;
import com.marketplace.compra.Service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compras")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @PostMapping
    public ResponseEntity<Compra> crearCompra(@RequestBody Compra compra) {
        return ResponseEntity.ok(compraService.crearCompra(compra));
    }

    @GetMapping
    public ResponseEntity<List<Compra>> obtenerTodas() {
        return ResponseEntity.ok(compraService.obtenerTodas());
    }

    @DeleteMapping("/{id}")
public ResponseEntity<Void> eliminarCompra(@PathVariable Long id) {
    compraService.eliminarCompra(id);
    return ResponseEntity.noContent().build(); 
}

}
