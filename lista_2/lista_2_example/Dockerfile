FROM maven:3.9.12-eclipse-temurin-25
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package
CMD ["java", "-jar", "target/grade-report-1.0.0-SNAPSHOT-jar-with-dependencies.jar"]
