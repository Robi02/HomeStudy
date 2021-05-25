package com.de4bi.study.restapiwithspring.index;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.de4bi.study.restapiwithspring.events.EventController;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
    ResourceSupport changed to RepresentationModel
    Resource changed to EntityModel
    Resources changed to CollectionModel
    PagedResources changed to PagedModel
    ResourceAssembler changed to RepresentationModelAssembler
*/

@RestController
public class IndexController {
    
    @GetMapping("/api")
    public RepresentationModel index() {
        RepresentationModel representationModel = new RepresentationModel();
        representationModel.add(linkTo(EventController.class).withRel("events"));
        return representationModel;
    }
}
