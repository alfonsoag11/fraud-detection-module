package com.example.FraudDet.Helpers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.FraudDet.Status;
import com.example.FraudDet.Controllers.TransactionController;
import com.example.FraudDet.Entities.Transaction;

@Component
public class TransactionModelAssembler implements RepresentationModelAssembler<Transaction, EntityModel<Transaction>> {

	@Override
	public EntityModel<Transaction> toModel(Transaction transaction) {

		// Unconditional links to single-item resource and aggregate root

		EntityModel<Transaction> transactionModel = EntityModel.of(transaction,
				linkTo(methodOn(TransactionController.class).oneItem(transaction.getId())).withSelfRel(),
				linkTo(methodOn(TransactionController.class).all()).withRel("transactions"));

		// Conditional links based on state of the transaction

		if (transaction.getStatus() == Status.IN_PROGRESS) {
			transactionModel.add(linkTo(methodOn(TransactionController.class).cancel(transaction.getId())).withRel("cancel"));
			transactionModel.add(linkTo(methodOn(TransactionController.class).complete(transaction.getId())).withRel("complete"));
		}

		return transactionModel;
	}
}
