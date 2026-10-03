package com.mycompany.grades;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

class StudentTest {

    @Test
    void computesAverage() {
        Student student = new Student("Jan", "Kowalski", List.of(4.0, 3.5, 5.0));

        assertEquals(4.1667, student.average(), 0.0001);
    }

    @Test
    void averageOfNoGradesIsZero() {
        assertEquals(0.0, new Student("Jan", "Kowalski", List.of()).average());
    }
}
