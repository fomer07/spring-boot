package payroll;

import static org.springframework.hateoas.server.LinkBuilder.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.payroll.springboot.EmployeeController;
import com.payroll.springboot.OrderController;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @GetMapping
    public RepresentationModel index() {
        RepresentationModel rootResource = new RepresentationModel();
        rootResource.add(linkTo(methodOn(EmployeeController.class).all()).withRel("employees"));
        rootResource.add(linkTo(methodOn(OrderController.class).all()).withRel("orders"));
        return rootResource;
    }

}
