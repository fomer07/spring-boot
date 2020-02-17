package com.payroll.springboot;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.*;
import javax.swing.text.html.parser.Entity;

/**
 * CREATED BY Omer Faruk AY 2/7/2020
 */
@Component
public class OrderResourceAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>> {

    @Override
    public EntityModel<Order> toModel(Order order) {
        //Unconditional links to single-item resource and aggregate root
        EntityModel<Order> orderResource = new EntityModel<>(order,
                linkTo(methodOn(OrderController.class).one(order.getId())).withSelfRel(),
                linkTo(methodOn(OrderController.class).all()).withRel("orders")
                );
        // Conditional links based on state of the order
        if (Status.IN_PROGRESS==order.getStatus()){
            orderResource.add(
                    linkTo(methodOn(OrderController.class).cancel(order.getId())).withRel("cancel"));
            orderResource.add(
            linkTo(methodOn(OrderController.class)
            .complete(order.getId())).withRel("complete"));
        }
        return orderResource;

    }
}
