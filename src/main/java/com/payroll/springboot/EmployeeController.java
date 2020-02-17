package com.payroll.springboot;


import org.springframework.hateoas.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * CREATED BY Omer Faruk AY 2/6/2020
 */
@RestController
public class EmployeeController {
    private final EmployeeRepository repository;
    private final EmployeeResourceAssembler assembler;
    private final OrderResourceAssembler orderResourceAssembler;

    public EmployeeController(EmployeeRepository repository, EmployeeResourceAssembler assembler, OrderResourceAssembler orderResourceAssembler) {
        this.repository = repository;
        this.assembler = assembler;
        this.orderResourceAssembler = orderResourceAssembler;
    }

    @GetMapping("/employees")
    public CollectionModel<EntityModel<Employee>> all(){
        List<EntityModel<Employee>> employees=repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return new CollectionModel<>(employees,linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
    }
    @PostMapping("/employees")
    public ResponseEntity<?> newEmployee(@RequestBody Employee newEmployee)throws URISyntaxException {
        Employee employee = repository.save(newEmployee);
        EntityModel<Employee> resource  = assembler.toModel(employee);

        return ResponseEntity.created(new URI(resource.getLink("self").orElse(new Link("self")).getHref()))
                .body(resource);
    }

    @GetMapping("/employees/{id}")
    public EntityModel<Employee> one(@PathVariable Long id) {

    Employee employee = repository.findById(id)
            .orElseThrow(() -> new EmployeeNotFoundException(id));

    return assembler.toModel(employee);
}
    @PutMapping("/employees/{id}")
    public ResponseEntity<?> replaceEmployee(@RequestBody Employee newEmployee,
                                    @PathVariable Long id ) throws URISyntaxException {
    Employee updatedEmployee =repository.findById(id)
        .map(employee -> {
            employee.setName(newEmployee.getName());
            employee.setRole(newEmployee.getRole());
            return repository.save(employee);
        })
            .orElseGet(()->{
                newEmployee.setId(id);
                return repository.save(newEmployee);
            });
    EntityModel<Employee> resource = assembler.toModel(updatedEmployee);
    return ResponseEntity
            .created(new URI(resource.getLink("self").orElse(new Link("self")).getHref()))
            .body(resource);
    }
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id){
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
