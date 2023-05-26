package de.claudioltamura.springboot.webflux.file;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

  private final ProjectRepository projectRepository;

  @GetMapping
  public Flux<Project> getAllAProjects() {
    return Flux.fromIterable(projectRepository.findAllProjects());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Mono<Project>> getProjectByName(@PathVariable String id) {
    var project = projectRepository.findProjectByName(id);
    if (project.isPresent()) {
      return ResponseEntity.ok(Mono.just(project.get()));
    } else {
      throw new NoSuchElementFoundException("Could not find project with name '" + id + "'.");
    }
  }
}
