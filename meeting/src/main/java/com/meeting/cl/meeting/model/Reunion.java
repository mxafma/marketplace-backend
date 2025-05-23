package com.meeting.cl.meeting.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Reunion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reunion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long usuarioId;

    @Column(nullable = false)
    private Long vendedorId;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false)
    private String codigoSeguridad;

    @Column(nullable = false)
    private String lugarEncuentro;

    @Column(nullable = false)
    private String estado; // pendiente, confirmada, cancelada, completada
}
