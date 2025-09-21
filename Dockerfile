# ===== STAGE 1: Build =====
FROM maven:3.9.9-amazoncorretto-23 AS build

# Set working directory
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# ===== STAGE 2: Run =====
FROM amazoncorretto:23-alpine

# Set working directory
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/MusicAPI-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/MusicAPI-0.0.1-SNAPSHOT.jar"]
