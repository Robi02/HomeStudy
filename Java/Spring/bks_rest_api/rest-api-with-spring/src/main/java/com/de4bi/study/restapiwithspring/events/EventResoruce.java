package com.de4bi.study.restapiwithspring.events;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class EventResoruce extends RepresentationModel {
    
   @JsonUnwrapped 
    private Event event;

    public EventResoruce(Event event) {
        this.event = event;
        add(linkTo(EventController.class).slash(event.getId()).withSelfRel());
    }

    public Event getEvent() {
        return event;
    }
}
