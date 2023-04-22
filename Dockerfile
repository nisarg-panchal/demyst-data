FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar demyst-data.jar
ENTRYPOINT ["java","-jar","/demyst-data.jar"]