package com.example.FraudDet.Entities;

import java.util.Objects;

import com.example.FraudDet.Status;
import com.example.FraudDet.RiskLevel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "USER_TRANSACTION")
public class Transaction {

	private @Id @GeneratedValue Long id;

	private String description;
	private float amount;
	private RiskLevel riskLevel;
	private Status status;

	public Transaction() {}

	public Transaction(String description, float amount, Status status, RiskLevel riskLevel) {
		this.description = description;
		this.amount = amount;
		this.status = status;
		this.riskLevel = riskLevel;
	}

	public Long getId() {
		return this.id;
	}

	public String getDescription() {
		return this.description;
	}

	public Status getStatus() {
		return this.status;
	}

	public float getAmount() {
		return this.amount;
	}

	public RiskLevel getRiskLevel() {
		return this.riskLevel;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public void setRiskLevel(RiskLevel riskLevel) {
		this.riskLevel = riskLevel;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;
		if (!(o instanceof Transaction))
			return false;
		Transaction transaction = (Transaction) o;
		return Objects.equals(this.id, transaction.id) && Objects.equals(this.description, transaction.description)
				&& this.status == transaction.status && this.amount == transaction.amount && this.riskLevel == transaction.riskLevel;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.description, this.status, this.riskLevel,  this.amount);
	}

	@Override
	public String toString() {
		return "Transaction{" + "id=" + this.id + ", description='" + this.description + '\'' + ", status=" + this.status + ", risk level=" + this.riskLevel + ", amount=" + this.amount + '}';
	}
}
