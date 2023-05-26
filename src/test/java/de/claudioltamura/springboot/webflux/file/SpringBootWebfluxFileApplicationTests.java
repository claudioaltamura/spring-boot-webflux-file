package de.claudioltamura.springboot.webflux.file;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootWebfluxFileApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	@DisplayName("API Test: get project list")
	void shouldReturnList() {
		webTestClient.get()
				.uri("/projects")
				.header(HttpHeaders.ACCEPT, "application/json")
				.exchange()
				.expectStatus().isOk()
				.expectBodyList(Project.class)
				.hasSize(6);
	}

}
