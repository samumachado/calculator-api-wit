# Use an official OpenJDK runtime as the base image
FROM eclipse-temurin:17-jre-alpine

# Set working directory inside container
WORKDIR /app

# Copy the built jar file (adjust name/path accordingly)
COPY target/math-1.0-SNAPSHOT.jar app.jar

# Expose the port if your calculator module exposes one (remove if not)
EXPOSE 8082

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
