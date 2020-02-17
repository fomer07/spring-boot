package com.payroll.springboot;

import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.*;
/**
 * CREATED BY Omer Faruk AY 2/7/2020
 */
@Component
public class EmployeeResourceAssembler implements RepresentationModelAssembler<Employee,EntityModel<Employee>> {


    @Override
    public EntityModel<Employee> toModel(Employee employee) {
        return new EntityModel<>(employee,
                linkTo(methodOn(EmployeeController.class).one(employee.getId())).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).all()).withRel("employees"));

    }
}

