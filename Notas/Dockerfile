FROM openjdk:17-jdk-slim
ARG JAR_FILE=build/libs/Notas-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app_Notas.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app_Notas.jar"]
