package com.mycompany.grades;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class AppTest {

    @TempDir
    Path directory;

    @Test
    void readsGradesFromResourcesWhenPathIsNotSet() throws IOException {
        try (Reader reader = App.openGrades(null)) {
            assertEquals(3, new GradesReader().read(reader).size());
        }
    }

    @Test
    void readsGradesFromGivenFile() throws IOException {
        Path file = directory.resolve("grades.csv");
        Files.writeString(file, "firstName,lastName,math\nola,lis,5.0\n");

        try (Reader reader = App.openGrades(file.toString())) {
            assertEquals(List.of(new Student("Ola", "Lis", List.of(5.0))), new GradesReader().read(reader));
        }
    }
}
