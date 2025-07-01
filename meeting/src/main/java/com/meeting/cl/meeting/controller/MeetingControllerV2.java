package com.meeting.cl.meeting.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.meeting.cl.meeting.assemblers.MeetingModelAssembler;
import com.meeting.cl.meeting.model.Meeting;
import com.meeting.cl.meeting.service.MeetingService;

@RestController
@RequestMapping("/api/v2/meetings")
public class MeetingControllerV2 {

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private MeetingModelAssembler assembler;

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Meeting> getMeetingById(@PathVariable Long id) {
        Meeting meeting = meetingService.getMeetingById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return assembler.toModel(meeting);
    }

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Meeting>> getAllMeetingsV2() {
        var meetings = meetingService.getAllMeetings().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(
            meetings,
            linkTo(methodOn(MeetingControllerV2.class).getAllMeetingsV2()).withSelfRel()
        );
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Meeting>> createMeeting(@RequestBody Meeting meeting, UriComponentsBuilder uriBuilder) {
        Meeting saved = meetingService.saveMeeting(meeting);

        EntityModel<Meeting> model = assembler.toModel(saved);

        return ResponseEntity
            .created(uriBuilder.path("/api/v2/meetings/{id}").buildAndExpand(saved.getId()).toUri())
            .body(model);
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Meeting> updateMeeting(@PathVariable Long id, @RequestBody Meeting meeting) {
        Meeting existing = meetingService.getMeetingById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // Actualizar campos
        existing.setUsuarioId(meeting.getUsuarioId());
        existing.setVendedorId(meeting.getVendedorId());
        existing.setFecha(meeting.getFecha());
        existing.setCodigoSeguridad(meeting.getCodigoSeguridad());
        existing.setLugarEncuentro(meeting.getLugarEncuentro());
        existing.setEstado(meeting.getEstado());

        Meeting updated = meetingService.saveMeeting(existing);
        return assembler.toModel(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMeeting(@PathVariable Long id) {
        Meeting meeting = meetingService.getMeetingById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        meetingService.deleteMeeting(id);
    }

    @GetMapping(value = "/usuario/{usuarioId}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Meeting>> getMeetingsByUsuarioId(@PathVariable Long usuarioId) {
        List<Meeting> meetings = meetingService.getMeetingsByUsuarioId(usuarioId);

        if (meetings.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay meetings para ese usuario");
        }

        List<EntityModel<Meeting>> models = meetings.stream()
            .map(assembler::toModel)
            .toList();

        return CollectionModel.of(
            models,
            linkTo(methodOn(MeetingControllerV2.class).getMeetingsByUsuarioId(usuarioId)).withSelfRel()
        );
    }
}
