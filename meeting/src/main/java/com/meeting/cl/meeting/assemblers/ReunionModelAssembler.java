package com.meeting.cl.meeting.assemblers;

import com.meeting.cl.meeting.controller.ReunionControllerV2;
import com.meeting.cl.meeting.model.Reunion;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ReunionModelAssembler implements RepresentationModelAssembler<Reunion, EntityModel<Reunion>> {

    @Override
    public EntityModel<Reunion> toModel(Reunion reunion) {
        return EntityModel.of(reunion,
                linkTo(methodOn(ReunionControllerV2.class).getReunionById(reunion.getId())).withSelfRel(),
                linkTo(methodOn(ReunionControllerV2.class).getAllReunionesV2()).withRel("reuniones")
        );
    }
}