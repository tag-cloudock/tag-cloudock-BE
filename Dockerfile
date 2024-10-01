FROM openjdk:17-jdk

WORKDIR /app
COPY build/libs/*.jar app.jar
RUN mkdir -p /app/src/main/resources
ARG APPLICATION_YML
RUN echo "$APPLICATION_YML" > /app/src/main/resources/application.yml
ENTRYPOINT ["java", "-jar", "app.jar"]
