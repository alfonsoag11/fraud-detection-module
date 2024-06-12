package com.example.FraudDet.Controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

	@GetMapping
	RepresentationModel<?> index() {

		RepresentationModel<?> rootModel = new RepresentationModel<>();
		rootModel.add(linkTo(methodOn(UserController.class).all()).withRel("users"));
		rootModel.add(linkTo(methodOn(TransactionController.class).all()).withRel("transactions"));
		return rootModel;
	}

}
