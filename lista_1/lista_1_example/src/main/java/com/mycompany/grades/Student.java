package com.mycompany.grades;

import java.util.List;

/** Student wraz z listą ocen. */
public record Student(String firstName, String lastName, List<Double> grades) {

    public Student {
        grades = List.copyOf(grades);
    }

    /** Średnia ocen; 0.0, gdy student nie ma żadnej oceny. */
    public double average() {
        return grades.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }
}
