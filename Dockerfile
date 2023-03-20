FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/unit.conversions-0.0.1-SNAPSHOT
EXPOSE 8080
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]