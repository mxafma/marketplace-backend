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

import com.meeting.cl.meeting.model.Meeting;
import com.meeting.cl.meeting.service.MeetingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/meetings")
@Tag(name = "Meetings", description = "Operaciones relacionadas con la coordinación de meetings entre usuarios")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @GetMapping
    @Operation(summary = "Obtener todas las meetings", description = "Devuelve una lista de todas las meetings registradas")
    @ApiResponse(responseCode = "200", description = "Listado de meetings obtenido exitosamente",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Meeting.class)))
    public List<Meeting> getAll() {
        return meetingService.getAllMeetings();
    }

    @PostMapping
    @Operation(summary = "Crear una nueva meeting", description = "Crea una nueva meeting entre usuarios")
    @ApiResponse(responseCode = "200", description = "Meeting creada exitosamente",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Meeting.class)))
    public Meeting create(@RequestBody Meeting meeting) {
        return meetingService.saveMeeting(meeting);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una meeting por ID", description = "Devuelve los datos de una meeting específica según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meeting encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Meeting.class))),
            @ApiResponse(responseCode = "404", description = "Meeting no encontrada")
    })
    public Optional<Meeting> getById(@PathVariable Long id) {
        return meetingService.getMeetingById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una meeting por ID", description = "Actualiza los datos de una meeting específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meeting actualizada correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Meeting.class))),
            @ApiResponse(responseCode = "404", description = "Meeting no encontrada")
    })
    public Meeting update(@PathVariable Long id, @RequestBody Meeting meeting) {
        return meetingService.updateMeeting(id, meeting);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una meeting por ID", description = "Elimina una meeting específica por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meeting eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Meeting no encontrada")
    })
    public void delete(@PathVariable Long id) {
        meetingService.deleteMeeting(id);
    }

    @GetMapping("/user/{usuarioId}")
    @Operation(summary = "Obtener meetings por usuario", description = "Devuelve todas las meetings en las que participa un usuario (como comprador o vendedor)")
    @ApiResponse(responseCode = "200", description = "Listado de meetings del usuario obtenido",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Meeting.class)))
    public List<Meeting> getByUsuarioId(@PathVariable Long usuarioId) {
        return meetingService.getMeetingsByUsuarioId(usuarioId);
    }
}