package com.example.FraudDet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.FraudDet.Entities.User;
import com.example.FraudDet.Entities.Transaction;
import com.example.FraudDet.Repositories.UserRepository;
import com.example.FraudDet.Repositories.TransactionRepository;

@Configuration
class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initDatabase(UserRepository UserRepository, TransactionRepository transactionRepository) {

		return args -> {
			UserRepository.save(new User("Albert", "Green", "+5245567812", "a.g.h@locomail.com"));
			UserRepository.save(new User("Shania", "Lopez", "+5234231234", "shania_lz@myemail.com"));

			UserRepository.findAll().forEach(user -> log.info("Preloaded " + user));

			
			transactionRepository.save(new Transaction("Walmart Rio Frio", 80.30f, Status.CANCELLED, RiskLevel.LOW));
			transactionRepository.save(new Transaction("Oxxo San Pedro", 20.00f, Status.IN_PROGRESS, RiskLevel.MEDIUM));
			transactionRepository.save(new Transaction("ML Automatic  payment", 30.10f ,Status.IN_PROGRESS, RiskLevel.MEDIUM));
			transactionRepository.save(new Transaction("Uber eats", 23.91f, Status.COMPLETED, RiskLevel.HIGH));

			transactionRepository.findAll().forEach(transaction -> {
				log.info("Preloaded " + transaction);
			});
			
		};
	}
}
