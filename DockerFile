# Use a slim, official OpenJDK image as the base
FROM openjdk:17-jdk-slim

# Copy the application's JAR file into the container
COPY target/*.jar app.jar

# Expose the port your application listens on
EXPOSE 8080

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]