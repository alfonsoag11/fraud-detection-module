package com.example.FraudDet.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.FraudDet.Entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
