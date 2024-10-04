FROM openjdk:17-jdk
ENV GOOGLE_APPLICATION_CREDENTIALS=/app/assets/google/key.json
WORKDIR /app
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]