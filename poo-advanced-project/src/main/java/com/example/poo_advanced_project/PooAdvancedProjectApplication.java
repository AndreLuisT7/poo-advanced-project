package com.example.poo_advanced_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PooAdvancedProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(PooAdvancedProjectApplication.class, args);
	}
}
