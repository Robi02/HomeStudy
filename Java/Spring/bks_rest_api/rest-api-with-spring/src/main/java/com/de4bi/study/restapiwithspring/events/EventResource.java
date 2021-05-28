package com.de4bi.study.restapiwithspring.events;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.validation.Errors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class EventResource extends EntityModel<Errors> {
    
    private static WebMvcLinkBuilder selfLinkBuilder = linkTo(EventController.class);

    private EventResource(){ }

    public static EntityModel<Event> of(Event event, String profile){
        List<Link> links = getSelfLink(event);
        links.add(Link.of(profile, "profile"));
        return EntityModel.of(event, links);
    }

    public static EntityModel<Event> of(Event event){
        List<Link> links = getSelfLink(event);
        return EntityModel.of(event, links);
    }

    private static List<Link> getSelfLink(Event event) {
        selfLinkBuilder.slash(event.getId());
        List<Link> links = new ArrayList<>();
        links.add(selfLinkBuilder.withSelfRel());
        return links;
    }

    public static URI getCreatedUri(Event event) {
        return selfLinkBuilder.slash(event.getId()).toUri();
    }
}
