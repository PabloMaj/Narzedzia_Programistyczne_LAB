package com.mycompany.grades;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

/**
 * Wczytuje studentów z pliku CSV za pomocą biblioteki Apache Commons CSV.
 * Pierwszy wiersz to nagłówek: firstName,lastName, a następnie dowolna liczba kolumn z ocenami.
 */
public class GradesReader {

    private static final CSVFormat FORMAT = CSVFormat.DEFAULT.builder()
            .setHeader()                // nazwy kolumn bierzemy z pierwszego wiersza
            .setSkipHeaderRecord(true)  // ...i nie traktujemy go jako danych
            .setTrim(true)
            .get();

    public List<Student> read(Reader input) throws IOException {
        List<Student> students = new ArrayList<>();
        try (CSVParser parser = CSVParser.parse(input, FORMAT)) {
            List<String> gradeColumns = parser.getHeaderNames().subList(2, parser.getHeaderNames().size());
            for (CSVRecord row : parser) {
                List<Double> grades = new ArrayList<>();
                for (String column : gradeColumns) {
                    grades.add(Double.parseDouble(row.get(column)));
                }
                students.add(new Student(
                        normalizeName(row.get("firstName")),
                        normalizeName(row.get("lastName")),
                        grades));
            }
        }
        return students;
    }

    /** "jAN" -> "Jan" – korzystamy z StringUtils z biblioteki Apache Commons Lang. */
    static String normalizeName(String name) {
        return StringUtils.capitalize(StringUtils.lowerCase(name));
    }
}
