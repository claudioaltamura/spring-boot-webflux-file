package de.claudioltamura.springboot.webflux.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootWebfluxFileApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebfluxFileApplication.class, args);
	}

	@Bean
	public ProjectRepository projectRepository() {
		return new ProjectRepository("projects.csv");
	}

}
