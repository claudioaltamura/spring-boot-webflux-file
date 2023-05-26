package de.claudioltamura.springboot.webflux.file;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class ProjectRepository {

    @NonNull
    private String csvFile;

    private final List<Project> projects = new ArrayList<>();

    @PostConstruct
    void postConstruct() {
        readCSVFile();
    }

    private void readCSVFile() {
        var filePath = new ClassPathResource(csvFile, ProjectRepository.class.getClassLoader());
        var csvParser = new CSVParserBuilder().withSeparator(',').build();
        try (Reader reader = Files.newBufferedReader(filePath.getFile().toPath());
             CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(csvParser).withSkipLines(1).build()) {
                String[] line;
                while ((line = csvReader.readNext()) != null) {
                    projects.add(new Project(line[0]
                            , Integer.parseInt(line[1])
                            , Integer.parseInt(line[2])
                            , Integer.parseInt(line[3])
                    ));
                }
            } catch (IOException | CsvValidationException ex) {
            throw new RuntimeException(csvFile + " not found!" ,ex);
        }
        log.info("csv file '{}' loaded.", csvFile);
    }

    public List<Project> findAllProjects() {
        return projects;
    }

    public Optional<Project> findProjectByName(String name) {
        return projects.stream().filter(p -> p.library().equals(name)).findFirst();
    }

}
