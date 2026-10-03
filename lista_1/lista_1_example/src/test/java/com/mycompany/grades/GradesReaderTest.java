package com.mycompany.grades;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.junit.jupiter.api.Test;

class GradesReaderTest {

    @Test
    void readsStudentsFromCsv() throws IOException {
        String csv = """
                firstName,lastName,math,physics
                jan, KOWALSKI ,4.0,3.0
                Anna,Nowak,5.0,4.5
                """;

        List<Student> students = new GradesReader().read(new StringReader(csv));

        assertEquals(List.of(
                new Student("Jan", "Kowalski", List.of(4.0, 3.0)),
                new Student("Anna", "Nowak", List.of(5.0, 4.5))), students);
    }

    @Test
    void normalizesName() {
        assertEquals("Wiśniewski", GradesReader.normalizeName("wIŚNIEWSKI"));
    }
}
