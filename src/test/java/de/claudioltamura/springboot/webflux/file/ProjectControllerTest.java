package de.claudioltamura.springboot.webflux.file;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(controllers = ProjectController.class)
class ProjectControllerTest {

  @MockBean private ProjectRepository projectRepository;

  @Autowired private WebTestClient webTestClient;

  @Test
  @DisplayName("should return a list")
  void shouldReturnList() {
    when(projectRepository.findAllProjects()).thenReturn(projects());

    webTestClient
        .get()
        .uri("/projects")
        .header(HttpHeaders.ACCEPT, "application/json")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(Project.class);

    verify(projectRepository, times(1)).findAllProjects();
  }

  private List<Project> projects() {
    return List.of(new Project("A", 1, 2, 3));
  }

  @Test
  @DisplayName("should return a project")
  void shouldReturnProject() {
    when(projectRepository.findProjectByName(anyString()))
        .thenReturn(Optional.of(new Project("A", 1, 2, 3)));

    webTestClient
        .get()
        .uri("/projects/A")
        .header(HttpHeaders.ACCEPT, "application/json")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.library")
        .isEqualTo("A");

    verify(projectRepository, times(1)).findProjectByName(anyString());
  }
}
