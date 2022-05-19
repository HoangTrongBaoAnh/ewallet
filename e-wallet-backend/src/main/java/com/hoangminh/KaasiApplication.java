package com.hoangminh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class KaasiApplication {

	public static void main(String[] args) {
		SpringApplication.run(KaasiApplication.class, args);
	}

}
