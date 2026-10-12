# Lista 2 – projekt przykładowy: raport ocen studentów (wtyczki Maven + Docker)

Rozszerzenie projektu przykładowego z listy 1. Pokazuje, jak:

- podpiąć wtyczki Maven do konkretnych etapów budowania (`<executions>`, `<phase>`, `<goal>`),
- zbudować wykonywalną paczkę JAR (`java -jar`),
- zbudować obraz Docker na podstawie pliku `Dockerfile`,
- uruchomić kontener z pliku `compose.yaml` i udostępnić mu pliki z komputera (`volumes`).

## Opis zadania

Rozszerz aplikację „raport ocen studentów” z listy 1.

1. **Etap `validate`.** Dodaj wtyczkę
   [maven-enforcer-plugin](https://mvnrepository.com/artifact/org.apache.maven.plugins/maven-enforcer-plugin/3.6.1)
   w wersji `3.6.1`. Podepnij jej cel `enforce` pod etap `validate` i skonfiguruj reguły
   `requireJavaVersion` (`[25,)`) oraz `requireMavenVersion` (`[3.9,)`). Sprawdź, co się stanie,
   gdy zmienisz wymaganą wersję Javy np. na `[99,)`.
2. **Etap `package`.** Dodaj wtyczkę
   [maven-assembly-plugin](https://mvnrepository.com/artifact/org.apache.maven.plugins/maven-assembly-plugin/3.7.1)
   w wersji `3.7.1`. Podepnij jej cel `single` pod etap `package`, użyj deskryptora
   `jar-with-dependencies` i ustaw w manifeście `mainClass` = `com.mycompany.grades.App`.
   Paczkę `target/grade-report-1.0.0-SNAPSHOT-jar-with-dependencies.jar` uruchom poleceniem `java -jar`.
3. **Plik z ocenami spoza JAR.** Zmień aplikację tak, aby – gdy ustawiona jest zmienna środowiskowa
   `GRADES_FILE` – wczytywała oceny z pliku o podanej ścieżce. Gdy zmienna nie jest ustawiona,
   aplikacja nadal korzysta z `grades.csv` z katalogu zasobów.
4. **Obraz Docker.** W katalogu głównym projektu utwórz `Dockerfile`, który na bazie obrazu
   `maven:3.9.12-eclipse-temurin-25` zbuduje paczkę JAR i uruchomi ją przy starcie kontenera.
5. **Kontener.** W nowym katalogu `docker/` utwórz plik `compose.yaml`, który:
   - tworzy kontener `grade-report` z obrazu `grade-report:1`,
   - ustawia zmienną środowiskową `GRADES_FILE` na `/data/grades.csv`,
   - za pomocą `volumes` udostępnia kontenerowi katalog `docker/data` z komputera jako `/data`.

   Zmień oceny w `docker/data/grades.csv` i uruchom kontener ponownie – raport powinien się zmienić
   bez przebudowy obrazu.

## Wymagania

- JDK 25 i Maven 3.9+ – do budowania lokalnego,
- Docker Desktop (Windows/macOS) lub Docker Engine (Linux) – do punktów 4–5,
- dostęp do internetu przy pierwszym budowaniu.

## Uruchomienie lokalne (Maven)

W katalogu `lista_2_example`:

```bash
mvn clean package
java -jar target/grade-report-1.0.0-SNAPSHOT-jar-with-dependencies.jar
```

Wynik (dane z `src/main/resources/grades.csv`):

```
Student                  Średnia
--------------------------------
Jan Kowalski             4.17
Anna Nowak               4.83
Piotr Wiśniewski         3.33
```

Odczyt ocen z innego pliku:

```bash
# Linux / macOS / Git Bash
GRADES_FILE=docker/data/grades.csv java -jar target/grade-report-1.0.0-SNAPSHOT-jar-with-dependencies.jar
```

```powershell
# Windows PowerShell
$env:GRADES_FILE = "docker\data\grades.csv"; java -jar target\grade-report-1.0.0-SNAPSHOT-jar-with-dependencies.jar
```

W konsoli Windows polskie znaki naprawia `chcp 65001`.

## Uruchomienie w Dockerze

```bash
# 1. zbudowanie obrazu (w katalogu lista_2_example, obok Dockerfile)
docker build -t grade-report:1 .

# 2. utworzenie i uruchomienie kontenera (w katalogu docker/, obok compose.yaml)
cd docker
docker compose up

# 3. ponowne uruchomienie kontenera, np. po zmianie docker/data/grades.csv
docker start -a grade-report
```

Oczekiwany wynik (dane z `docker/data/grades.csv`):

```
Plik z ocenami: /data/grades.csv
Student                  Średnia
--------------------------------
Ola Lis                  4.83
Tomasz Zieliński         3.83
```

Usunięcie kontenera: `docker compose down` (w katalogu `docker/`).

## Struktura projektu

```
lista_2_example/
├── pom.xml                  # zależności + wtyczki enforcer i assembly
├── Dockerfile
├── .dockerignore
├── docker/
│   ├── compose.yaml
│   └── data/grades.csv      # plik widoczny w kontenerze jako /data/grades.csv
└── src/
    ├── main/
    │   ├── java/com/mycompany/grades/
    │   │   ├── App.java             # wybór źródła danych + wyświetlenie raportu
    │   │   ├── GradesReader.java    # wczytywanie CSV (Commons CSV)
    │   │   └── Student.java
    │   └── resources/grades.csv
    └── test/java/com/mycompany/grades/
        ├── AppTest.java
        ├── GradesReaderTest.java
        └── StudentTest.java
```
