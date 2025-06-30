package com.meeting.cl.meeting.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meeting.cl.meeting.model.Reunion;
import com.meeting.cl.meeting.service.ReunionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/meetings")
@Tag(name = "Reuniones", description = "Operaciones relacionadas con la coordinación de reuniones entre usuarios")
public class ReunionController {

    @Autowired
    private ReunionService reunionService;

    @GetMapping
    @Operation(summary = "Obtener todas las reuniones", description = "Devuelve una lista de todas las reuniones registradas")
    @ApiResponse(responseCode = "200", description = "Listado de reuniones obtenido exitosamente",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Reunion.class)))
    public List<Reunion> getAll() {
        return reunionService.getAllReuniones();
    }

    @PostMapping
    @Operation(summary = "Crear una nueva reunión", description = "Crea una nueva reunión entre usuarios")
    @ApiResponse(responseCode = "200", description = "Reunión creada exitosamente",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Reunion.class)))
    public Reunion create(@RequestBody Reunion reunion) {
        return reunionService.saveReunion(reunion);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una reunión por ID", description = "Devuelve los datos de una reunión específica según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reunión encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Reunion.class))),
            @ApiResponse(responseCode = "404", description = "Reunión no encontrada")
    })
    public Optional<Reunion> getById(@PathVariable Long id) {
        return reunionService.getReunionById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una reunión por ID", description = "Actualiza los datos de una reunión específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reunión actualizada correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Reunion.class))),
            @ApiResponse(responseCode = "404", description = "Reunión no encontrada")
    })
    public Reunion update(@PathVariable Long id, @RequestBody Reunion reunion) {
        return reunionService.updateReunion(id, reunion);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una reunión por ID", description = "Elimina una reunión específica por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reunión eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Reunión no encontrada")
    })
    public void delete(@PathVariable Long id) {
        reunionService.deleteReunion(id);
    }

    @GetMapping("/user/{usuarioId}")
    @Operation(summary = "Obtener reuniones por usuario", description = "Devuelve todas las reuniones en las que participa un usuario (como comprador o vendedor)")
    @ApiResponse(responseCode = "200", description = "Listado de reuniones del usuario obtenido",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Reunion.class)))
    public List<Reunion> getByUsuarioId(@PathVariable Long usuarioId) {
        return reunionService.getReunionsByUsuarioId(usuarioId);
    }
}