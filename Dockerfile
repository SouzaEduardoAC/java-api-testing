# Stage 1: Build the application using Maven and JDK 21
FROM maven:3.9.7-eclipse-temurin-21 AS build
WORKDIR /app

# Copy the Maven project definition
COPY pom.xml .

# Download dependencies first to leverage Docker layer caching
RUN mvn dependency:go-offline

# Copy the rest of the source code
COPY src ./src

# Build the project and create the JAR file, skipping tests for a faster build
RUN mvn package -DskipTests

# Stage 2: Create the final, smaller JRE image for running the application
FROM eclipse-temurin:21-jre-alpine

# Create a non-root user and group for security purposes
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR /app

# Copy the executable JAR from the build stage using a wildcard for resilience
COPY --from=build /app/target/*.jar app.jar

# Expose the port the application runs on (as configured in application.properties)
EXPOSE 80

# Switch to the non-root user before running the application
USER appuser

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]