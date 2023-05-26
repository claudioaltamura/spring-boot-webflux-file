package de.claudioltamura.springboot.webflux.file;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;

class ProjectRepositoryTest {

    private final ProjectRepository projectRepository = new ProjectRepository("test.csv");

    public ProjectRepositoryTest() {
        projectRepository.postConstruct();
    }

    @Test
    @DisplayName("should return a list")
    void shouldReturnList() {
        assertThat(projectRepository.findAllProjects()).isNotEmpty();
    }

    @Test
    @DisplayName("should return a project")
    void shouldReturnProject() {
        assertThat(projectRepository.findProjectByName("A")).isNotEmpty();
    }

}