package com.mycompany.grades;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

/**
 * Wyświetla raport średnich ocen studentów.
 *
 * <p>Jeśli ustawiona jest zmienna środowiskowa GRADES_FILE, dane są wczytywane z pliku
 * o podanej ścieżce (np. w kontenerze Docker). W przeciwnym razie używany jest plik
 * grades.csv z katalogu resources.
 */
public class App {

    private static final String GRADES_RESOURCE = "/grades.csv";
    private static final String GRADES_FILE_ENV = "GRADES_FILE";

    /**
     * Punkt wejścia aplikacji.
     *
     * @param args argumenty wiersza poleceń (nieużywane)
     * @throws IOException gdy nie uda się odczytać pliku z ocenami
     */
    public static void main(String[] args) throws IOException {
        List<Student> students;
        try (Reader reader = openGrades(System.getenv(GRADES_FILE_ENV))) {
            students = new GradesReader().read(reader);
        }

        System.out.println(StringUtils.rightPad("Student", 25) + "Średnia");
        System.out.println(StringUtils.repeat('-', 32));
        for (Student s : students) {
            String name = s.firstName() + " " + s.lastName();
            System.out.println(StringUtils.rightPad(name, 25) + String.format(Locale.ROOT, "%.2f", s.average()));
        }
    }

    /** Otwiera plik wskazany ścieżką lub – gdy ścieżka jest pusta – plik z zasobów. */
    static Reader openGrades(String path) throws IOException {
        if (StringUtils.isNotBlank(path)) {
            System.out.println("Plik z ocenami: " + path);
            return Files.newBufferedReader(Path.of(path), StandardCharsets.UTF_8);
        }
        InputStream stream = App.class.getResourceAsStream(GRADES_RESOURCE);
        if (stream == null) {
            throw new IllegalStateException("Nie znaleziono pliku " + GRADES_RESOURCE + " w zasobach");
        }
        return new InputStreamReader(stream, StandardCharsets.UTF_8);
    }
}
