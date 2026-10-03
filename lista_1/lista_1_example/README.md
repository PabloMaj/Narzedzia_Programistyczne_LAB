# Lista 1 – projekt przykładowy: raport ocen studentów

Przykładowy projekt Maven pokazujący, jak:

- zdefiniować projekt w pliku `pom.xml` (`groupId`, `artifactId`, `version`, `properties`),
- dodać zależności od zewnętrznych bibliotek (`dependencies`),
- umieścić kod w `src/main/java`, a zasoby w `src/main/resources`,
- napisać i uruchomić testy jednostkowe (`src/test/java`).

## Opis zadania

Napisz aplikację, która przygotuje raport średnich ocen studentów.

1. Utwórz projekt Maven o współrzędnych
   `groupId` = `com.mycompany.grades`, `artifactId` = `grade-report`, `version` = `1.0.0-SNAPSHOT`.
   Dodaj zależności od bibliotek:
   - [Apache Commons CSV](https://mvnrepository.com/artifact/org.apache.commons/commons-csv/1.14.1) `1.14.1`,
   - [Apache Commons Lang](https://mvnrepository.com/artifact/org.apache.commons/commons-lang3/3.18.0) `3.18.0`.
2. W katalogu `src/main/resources` utwórz plik `grades.csv`. Pierwszy wiersz to nagłówek:
   `firstName,lastName`, a po nich kolumny z ocenami z kolejnych przedmiotów, np.:
   ```csv
   firstName,lastName,math,physics,programming
   jan,kowalski,4.0,3.5,5.0
   ANNA,NOWAK,5.0,4.5,5.0
   ```
3. Korzystając z biblioteki **Apache Commons CSV**, wczytaj plik `grades.csv` do listy obiektów
   klasy `Student` (imię, nazwisko, lista ocen).
4. Korzystając z klasy `StringUtils` z biblioteki **Apache Commons Lang**:
   - znormalizuj imiona i nazwiska (pierwsza litera wielka, pozostałe małe: `ANNA` → `Anna`),
   - wyświetl raport w postaci wyrównanej tabeli z imieniem i nazwiskiem oraz średnią ocen
     (zaokrągloną do dwóch miejsc po przecinku).
5. Napisz testy jednostkowe (JUnit 5) sprawdzające wczytywanie pliku CSV i liczenie średniej.

Oczekiwany wynik dla dołączonego pliku `grades.csv`:

```
Student                  Średnia
--------------------------------
Jan Kowalski             4.17
Anna Nowak               4.83
Piotr Wiśniewski         3.33
```

## Wymagania

- JDK 25
- Maven 3.9+ (polecenie `mvn` dostępne w `PATH`)
- dostęp do internetu przy pierwszym budowaniu (pobranie zależności z Maven Central)

## Uruchomienie

Polecenia wykonujemy w katalogu `lista_1_example` (tam, gdzie leży `pom.xml`).

```bash
# uruchomienie testów jednostkowych
mvn test

# kompilacja i uruchomienie aplikacji
mvn compile exec:java

# zbudowanie pliku JAR (wynik w katalogu target/)
mvn package
```

## Struktura projektu

```
lista_1_example/
├── pom.xml                                  # konfiguracja projektu i zależności
└── src/
    ├── main/
    │   ├── java/com/mycompany/grades/
    │   │   ├── App.java                     # punkt wejścia – wyświetla raport
    │   │   ├── GradesReader.java            # wczytywanie CSV (Commons CSV)
    │   │   └── Student.java                 # model danych + liczenie średniej
    │   └── resources/
    │       └── grades.csv                   # dane wejściowe
    └── test/java/com/mycompany/grades/
        ├── GradesReaderTest.java
        └── StudentTest.java
```
