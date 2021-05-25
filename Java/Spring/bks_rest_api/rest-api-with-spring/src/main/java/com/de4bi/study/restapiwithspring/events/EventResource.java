package com.de4bi.study.restapiwithspring.events;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import org.springframework.hateoas.EntityModel;
import org.springframework.validation.Errors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class EventResource extends EntityModel<Errors> {
    
   @JsonUnwrapped 
    private Event event;

    public EventResource(Event event) {
        this.event = event;
        add(linkTo(EventController.class).slash(event.getId()).withSelfRel());
    }

    public Event getEvent() {
        return event;
    }
}
