package com.example.FraudDet.Helpers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.FraudDet.Controllers.UserController;
import com.example.FraudDet.Entities.User;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {

	@Override
	public EntityModel<User> toModel(User user) {

		return EntityModel.of(user, //
				linkTo(methodOn(UserController.class).oneItem(user.getId())).withSelfRel(),
				linkTo(methodOn(UserController.class).all()).withRel("users"));
	}
}
