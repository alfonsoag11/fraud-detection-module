package com.example.FraudDet.Controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.FraudDet.Entities.User;
import com.example.FraudDet.Helpers.UserModelAssembler;
import com.example.FraudDet.Helpers.UserNotFoundException;
import com.example.FraudDet.Helpers.TransactionModelAssembler;
import com.example.FraudDet.Repositories.UserRepository;

// tag::constructor[]
@RestController
public class UserController {

	private final UserRepository repository;

	private final UserModelAssembler assembler;
	private final TransactionModelAssembler orderAssembler;

	UserController(UserRepository repository, UserModelAssembler assembler,
			TransactionModelAssembler TransactionAssembler) {

		this.repository = repository;
		this.assembler = assembler;
		this.orderAssembler = TransactionAssembler;
	}
	// end::constructor[]

	// Aggregate root

	@GetMapping("/users")
	public CollectionModel<EntityModel<User>> all() {

		List<EntityModel<User>> users = repository.findAll().stream() //
				.map(assembler::toModel) //
				.collect(Collectors.toList());

		return CollectionModel.of(users, linkTo(methodOn(UserController.class).all()).withSelfRel());
	}

	@PostMapping("/users")
	ResponseEntity<?> newUser(@RequestBody User newUser) {

		EntityModel<User> entityModel = assembler.toModel(repository.save(newUser));

		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	// Single item

	@GetMapping("/users/{id}")
	public EntityModel<User> oneItem(@PathVariable Long id) {

		User user = repository.findById(id) //
				.orElseThrow(() -> new UserNotFoundException(id));

		return assembler.toModel(user);
	}

	@PutMapping("/users/{id}")
	ResponseEntity<?> replaceUser(@RequestBody User newUser, @PathVariable Long id) {

		User updatedUser = repository.findById(id) //
				.map(employee -> {
					employee.setName(newUser.getName());
					employee.setEmail(newUser.getEmail());
					return repository.save(employee);
				}) //
				.orElseGet(() -> {
					return repository.save(newUser);
				});

		EntityModel<User> entityModel = assembler.toModel(updatedUser);

		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@DeleteMapping("/users/{id}")
	ResponseEntity<?> deleteUser(@PathVariable Long id) {

		repository.deleteById(id);

		return ResponseEntity.noContent().build();
	}
}
