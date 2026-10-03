package com.mycompany.grades;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

/** Wyświetla raport średnich ocen studentów wczytanych z pliku grades.csv (z katalogu resources). */
public class App {

    private static final String GRADES_FILE = "/grades.csv";

    public static void main(String[] args) throws IOException {
        List<Student> students;
        try (InputStream stream = App.class.getResourceAsStream(GRADES_FILE)) {
            if (stream == null) {
                throw new IllegalStateException("Nie znaleziono pliku " + GRADES_FILE + " w zasobach");
            }
            Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
            students = new GradesReader().read(reader);
        }

        System.out.println(StringUtils.rightPad("Student", 25) + "Średnia");
        System.out.println(StringUtils.repeat('-', 32));
        for (Student s : students) {
            String name = s.firstName() + " " + s.lastName();
            System.out.println(StringUtils.rightPad(name, 25) + String.format(Locale.ROOT, "%.2f", s.average()));
        }
    }
}
