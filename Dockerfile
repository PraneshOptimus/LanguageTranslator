# Stage 1: The Build Stage
FROM maven:3.8.6-openjdk-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install -DskipTests

# Stage 2: The Final (Runtime) Stage
FROM openjdk:17-jre-slim
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]