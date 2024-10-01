FROM openjdk:17-jdk-slim
EXPOSE 8080
ARG JAR_FILE=./build/libs/pagether-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "-Dspring.profiles.active=prod", "-jar", "app.jar"]
