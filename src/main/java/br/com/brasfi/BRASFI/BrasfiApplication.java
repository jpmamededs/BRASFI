package br.com.brasfi.BRASFI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class BrasfiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BrasfiApplication.class, args);
	}

}
