# Use an official OpenJDK runtime as the base image
FROM eclipse-temurin:17-jre-alpine

# Set working directory inside container
WORKDIR /app

# Copy the built jar file (adjust name/path accordingly)
COPY target/rest-1.0-SNAPSHOT.jar app.jar

# Expose the port your app runs on
EXPOSE 8081

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
