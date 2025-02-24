FROM eclipse-temurin:21-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY target/form-collection-app-1.0.0-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
