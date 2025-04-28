FROM amazoncorretto:21-alpine-jdk

WORKDIR /app

COPY ./target/users-service-0.0.1-SNAPSHOT.jar users-service.jar

ENTRYPOINT ["java", "-jar", "users-service.jar"]
