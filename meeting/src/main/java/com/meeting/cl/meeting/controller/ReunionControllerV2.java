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

import com.meeting.cl.meeting.assemblers.ReunionModelAssembler;
import com.meeting.cl.meeting.model.Reunion;
import com.meeting.cl.meeting.service.ReunionService;

@RestController
@RequestMapping("/api/v2/meetings")
public class ReunionControllerV2 {

    @Autowired
    private ReunionService reunionService;

    @Autowired
    private ReunionModelAssembler assembler;

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Reunion> getReunionById(@PathVariable Long id) {
        Reunion reunion = reunionService.getReunionById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return assembler.toModel(reunion);
    }

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Reunion>> getAllReunionesV2() {
        var reuniones = reunionService.getAllReuniones().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(
            reuniones,
            linkTo(methodOn(ReunionControllerV2.class).getAllReunionesV2()).withSelfRel()
        );
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Reunion>> createReunion(@RequestBody Reunion reunion, UriComponentsBuilder uriBuilder) {
        Reunion saved = reunionService.saveReunion(reunion);

        EntityModel<Reunion> model = assembler.toModel(saved);

        return ResponseEntity
            .created(uriBuilder.path("/api/v2/meetings/{id}").buildAndExpand(saved.getId()).toUri())
            .body(model);
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Reunion> updateReunion(@PathVariable Long id, @RequestBody Reunion reunion) {
        Reunion existing = reunionService.getReunionById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // Actualizar campos
        existing.setUsuarioId(reunion.getUsuarioId());
        existing.setVendedorId(reunion.getVendedorId());
        existing.setFecha(reunion.getFecha());
        existing.setCodigoSeguridad(reunion.getCodigoSeguridad());
        existing.setLugarEncuentro(reunion.getLugarEncuentro());
        existing.setEstado(reunion.getEstado());

        Reunion updated = reunionService.saveReunion(existing);
        return assembler.toModel(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReunion(@PathVariable Long id) {
        Reunion reunion = reunionService.getReunionById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        reunionService.deleteReunion(id);
    }

    @GetMapping(value = "/usuario/{usuarioId}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Reunion>> getReunionesByUsuarioId(@PathVariable Long usuarioId) {
        List<Reunion> reuniones = reunionService.getReunionsByUsuarioId(usuarioId);

        if (reuniones.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay reuniones para ese usuario");
        }

        List<EntityModel<Reunion>> models = reuniones.stream()
            .map(assembler::toModel)
            .toList();

        return CollectionModel.of(
            models,
            linkTo(methodOn(ReunionControllerV2.class).getReunionesByUsuarioId(usuarioId)).withSelfRel()
        );
    }
}
