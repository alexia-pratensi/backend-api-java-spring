package fr.alexia.backendapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BackendApiApplication {
	private static final Logger logger = LoggerFactory.getLogger(BackendApiApplication.class);

	public static void main(String[] args) {
		logger.info("Application started!");

		SpringApplication.run(BackendApiApplication.class, args);
	}

}
