# Build stage: We use Maven and Java 21 to build the app
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage: We take the compiled jar and run it
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/target/utilfree-backend-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
