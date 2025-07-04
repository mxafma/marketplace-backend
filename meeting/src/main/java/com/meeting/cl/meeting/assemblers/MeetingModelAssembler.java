package com.meeting.cl.meeting.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.meeting.cl.meeting.controller.MeetingControllerV2;
import com.meeting.cl.meeting.model.Meeting;

@Component
public class MeetingModelAssembler implements RepresentationModelAssembler<Meeting, EntityModel<Meeting>> {

    @Override
    public EntityModel<Meeting> toModel(Meeting meeting) {
        return EntityModel.of(meeting,
                linkTo(methodOn(MeetingControllerV2.class).getMeetingById(meeting.getId())).withSelfRel(),
                linkTo(methodOn(MeetingControllerV2.class).getAllMeetingsV2()).withRel("meetings")
        );
    }
}