package com.example.FraudDet.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.FraudDet.Entities.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
