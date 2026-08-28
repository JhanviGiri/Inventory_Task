package com.project.Inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class InventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryApplication.class, args);
	}

//	@Bean
//	CommandLineRunner passwordGenerator(PasswordEncoder passwordEncoder) {
//		return args -> {
//			System.out.println("admin123 = " + passwordEncoder.encode("admin123"));
//			System.out.println("user123 = " + passwordEncoder.encode("user123"));
//			System.out.println("manager123 = " + passwordEncoder.encode("manager123"));
//		};
//	}
}
