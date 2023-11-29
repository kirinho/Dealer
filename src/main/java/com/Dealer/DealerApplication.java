package com.Dealer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.Dealer.repositories")
@EntityScan("com.Dealer.entities")
public class DealerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DealerApplication.class, args);
	}

}
