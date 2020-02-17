package com.payroll.springboot;


import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.mediatype.vnderrors.VndErrors;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * CREATED BY Omer Faruk AY 2/7/2020
 */
//tag ::main[]
@RestController
public class OrderController {
    private final OrderRepository orderRepository;
    private final OrderResourceAssembler assembler;

    public OrderController(OrderRepository orderRepository, OrderResourceAssembler assembler) {
        this.orderRepository = orderRepository;
        this.assembler = assembler;
    }
    @GetMapping("/orders")
    public CollectionModel<EntityModel<Order>> all(){
        List<EntityModel<Order>> orders= orderRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return new CollectionModel<>(orders,
                linkTo(methodOn(OrderController.class).all()).withSelfRel());
    }
    @GetMapping("/orders/{id}")
    public EntityModel<Order> one(@PathVariable Long id){
        return assembler.toModel(
                orderRepository.findById(id)
                .orElseThrow(()-> new OrderNotFoundException(id))
        );
    }
    @PostMapping("/orders")
    public ResponseEntity<EntityModel<Order>> newOrder(@RequestBody Order order){
        order.setStatus(Status.IN_PROGRESS);
        Order newOrder = orderRepository.save(order);

        return ResponseEntity
                .created(linkTo(methodOn(OrderController.class).one(newOrder.getId())).toUri())
                .body(assembler.toModel(newOrder));
    }
    //end::main[]
    //tag::delete[]
    @DeleteMapping("/orders/{id}/cancel")
    public ResponseEntity<RepresentationModel> cancel (@PathVariable Long id){
        Order order = orderRepository.findById(id).orElseThrow(()->new OrderNotFoundException(id));
        if(order.getStatus()==Status.IN_PROGRESS){
            order.setStatus(Status.CANCELLED);
            return ResponseEntity.ok(assembler.toModel(orderRepository.save(order)));
        }
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new VndErrors.VndError("Method not allowed", "You can't cancel an order that is in the " + order.getStatus() + " status"));
    }
    //end::delete
    //tag::compleete[]
    @PutMapping("/orders/{id}/complete")
    public ResponseEntity<RepresentationModel> complete(@PathVariable Long id){
        Order order = orderRepository.findById(id).orElseThrow(()-> new OrderNotFoundException(id));
        if (order.getStatus()==Status.IN_PROGRESS){
            order.setStatus(Status.COMPLETED);
            return ResponseEntity.ok(assembler.toModel(orderRepository.save(order)));
        }
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new VndErrors.VndError("Method not allowed", "You can't complete an order that is in the " + order.getStatus() + " status"));
    }
    //end::complete[]




}
