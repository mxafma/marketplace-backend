package com.marketplace.compra.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idUsuario;
    private Long idProducto;
    private LocalDateTime fechaCompra;
    private String estado;

    @PrePersist
    public void prePersist() {
        this.fechaCompra = LocalDateTime.now();
        if (this.estado == null) {
            this.estado = "PENDIENTE";
        }
    }
}

