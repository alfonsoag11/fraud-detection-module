package com.example.FraudDet.Controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.FraudDet.Status;
import com.example.FraudDet.Entities.Transaction;
import com.example.FraudDet.Helpers.TransactionModelAssembler;
import com.example.FraudDet.Helpers.TransactionNotFoundException;
import com.example.FraudDet.Repositories.TransactionRepository;

// tag::main[]
@RestController
public class TransactionController {

	private final TransactionRepository transactionRepository;
	private final TransactionModelAssembler assembler;

	TransactionController(TransactionRepository transactionRepository, TransactionModelAssembler assembler) {

		this.transactionRepository = transactionRepository;
		this.assembler = assembler;
	}

	@GetMapping("/transactions")
	public CollectionModel<EntityModel<Transaction>> all() {

		List<EntityModel<Transaction>> transaction = transactionRepository.findAll().stream() //
				.map(assembler::toModel) //
				.collect(Collectors.toList());

		return CollectionModel.of(transaction, //
				linkTo(methodOn(TransactionController.class).all()).withSelfRel());
	}

	// Single item
	@GetMapping("/transactions/{id}")
	public EntityModel<Transaction> oneItem(@PathVariable Long id) {

		Transaction transaction = transactionRepository.findById(id) //
				.orElseThrow(() -> new TransactionNotFoundException(id));

		return assembler.toModel(transaction);
	}

	@PostMapping("/transactions")
	ResponseEntity<EntityModel<Transaction>> newTransaction(@RequestBody Transaction transaction) {

		transaction.setStatus(Status.IN_PROGRESS);
		Transaction newTransaction = transactionRepository.save(transaction);

		return ResponseEntity //
				.created(linkTo(methodOn(TransactionController.class).oneItem(newTransaction.getId())).toUri()) //
				.body(assembler.toModel(newTransaction));
	}
	// end::main[]

	// tag::delete[]
	@DeleteMapping("/transactions/{id}/cancel")
	public ResponseEntity<?> cancel(@PathVariable Long id) {

		Transaction transaction = transactionRepository.findById(id) //
				.orElseThrow(() -> new TransactionNotFoundException(id));

		if (transaction.getStatus() == Status.IN_PROGRESS) {
			transaction.setStatus(Status.CANCELLED);
			return ResponseEntity.ok(assembler.toModel(transactionRepository.save(transaction)));
		}

		return ResponseEntity //
				.status(HttpStatus.METHOD_NOT_ALLOWED) //
				.header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
				.body(Problem.create() //
						.withTitle("Method not allowed") //
						.withDetail("You can't cancel an order that is in the " + transaction.getStatus() + " status"));
	}
	// end::delete[]

	// tag::complete[]
	@PutMapping("/transactions/{id}/complete")
	public ResponseEntity<?> complete(@PathVariable Long id) {

		Transaction transaction = transactionRepository.findById(id) //
				.orElseThrow(() -> new TransactionNotFoundException(id));

		if (transaction.getStatus() == Status.IN_PROGRESS) {
			transaction.setStatus(Status.COMPLETED);
			return ResponseEntity.ok(assembler.toModel(transactionRepository.save(transaction)));
		}

		return ResponseEntity //
				.status(HttpStatus.METHOD_NOT_ALLOWED) //
				.header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
				.body(Problem.create() //
						.withTitle("Method not allowed") //
						.withDetail("You can't complete a transaction that is in the " + transaction.getStatus() + " status"));
	}
	// end::complete[]
}
