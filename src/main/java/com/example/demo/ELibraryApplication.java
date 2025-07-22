package com.example.demo;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ELibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ELibraryApplication.class, args);
	}

	@Bean
	public CommandLineRunner entityScanner(EntityManagerFactory emf) {
		return args -> emf.getMetamodel().getEntities().forEach(e ->
				System.out.println("🧠 Hibernate sees: " + e.getName()));
	}

}
