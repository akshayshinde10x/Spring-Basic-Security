# Use a Java 25 JDK image to build the Spring Boot app
FROM eclipse-temurin:25-jdk AS build

# Set the working directory
WORKDIR /app

# Copy Maven wrapper and POM to take advantage of caching
COPY mvnw ./
COPY .mvn/ .mvn/
COPY pom.xml ./

# Ensure the Maven wrapper is executable
RUN chmod +x mvnw


# Download dependencies to cache them
RUN ./mvnwdependency:go-offline

# Copy the source code and build the application
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Use a Java 25 runtime image to run the application
FROM eclipse-temurin:25-jre

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port 8081 for the Spring Boot application
EXPOSE 8081

# Specify the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]


