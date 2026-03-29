package com.healthCareAnalyzer.Health_Care_Backend;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HealthCareBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthCareBackendApplication.class, args);
	}

}
