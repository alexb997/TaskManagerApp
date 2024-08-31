# Use a Maven image to build the project
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copy the project files to the container
COPY pom.xml .
COPY src ./src

# Build the Spring Boot application
RUN mvn clean package -DskipTests

# Use an OpenJDK image to run the built application
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built jar file from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
