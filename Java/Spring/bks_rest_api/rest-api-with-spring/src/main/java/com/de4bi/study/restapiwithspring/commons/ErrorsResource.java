package com.de4bi.study.restapiwithspring.commons;

import org.springframework.hateoas.EntityModel;
import org.springframework.validation.Errors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.de4bi.study.restapiwithspring.index.IndexController;

public class ErrorsResource extends EntityModel<Errors> {
    
    public static EntityModel<Errors> modelOf(Errors errors) {
        EntityModel<Errors> errorsModel = EntityModel.of(errors);
        errorsModel.add(linkTo(methodOn(IndexController.class).index()).withRel("index"));
        return errorsModel;
    }
}
