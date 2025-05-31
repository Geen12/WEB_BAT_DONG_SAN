package com.example.Intern;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InternApplication {
	public static void main(String[] args) {
		SpringApplication.run(InternApplication.class, args);
	}

	@Bean
	@SuppressWarnings("unused")
	CommandLineRunner runner() {
		return args -> {
			System.out.println("Done");
		};
	}
}