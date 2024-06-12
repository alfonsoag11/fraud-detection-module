package com.example.FraudDet.Entities;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "USER_CLIENT")
public class User {

	private @Id @GeneratedValue Long id;
	private String firstName;
	private String lastName;
	private String phone; 
	private String email;

	public User() {}

	public User(String firstName, String lastName, String phone, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.email = email;
	}

	public String getName() {
		return this.firstName + " " + this.lastName;
	}

	public void setName(String name) {

		String[] parts = name.split(" ");
		this.firstName = parts[0];
		this.lastName = parts[1];
	}

	public Long getId() {
		return this.id;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getPhone() {
		return this.phone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;
		if (!(o instanceof User))
			return false;
		User user = (User) o;
		return Objects.equals(this.id, user.id) && Objects.equals(this.firstName, user.firstName)
				&& Objects.equals(this.lastName, user.lastName) && Objects.equals(this.phone, user.phone) && Objects.equals(this.email, user.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.firstName, this.lastName, this.email);
	}

	@Override
	public String toString() {
		return "User{" + "id=" + this.id + ", firstName='" + this.firstName + '\'' + ", lastName='" + this.lastName
				+ '\'' + ", email='" + this.email + '\'' + ", phone='" + this.phone +  '\'' + '}';
	}
}
