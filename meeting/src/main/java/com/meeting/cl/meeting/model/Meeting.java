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
@Table(name = "Meeting")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único de la reunión

    @Column(nullable = false)
    private Long usuarioId; // Identificador del usuario comprador

    @Column(nullable = false) 
    private Long vendedorId; // Identificador del usuario vendedor

    @Column(nullable = false)
    private LocalDateTime fecha; // Fecha y hora de la reunión

    @Column(nullable = false)
    private String codigoSeguridad; // Código de seguridad para la reunión

    @Column(nullable = false)
    private String lugarEncuentro; // Lugar de encuentro para la reunión (proximamente se usará otra entidad para manejar lugares)

    @Column(nullable = false)
    private String estado; // pendiente, confirmada, cancelada, completada
}
