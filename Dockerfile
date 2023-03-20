FROM openjdk:8-jdk-alpine
MAINTAINER Renesh.Nandkishore
COPY target/unit.conversions-0.0.1-SNAPSHOT.jar unit.conversions.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/unit.conversions.jar"]