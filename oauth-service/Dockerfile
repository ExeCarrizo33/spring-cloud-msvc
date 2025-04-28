FROM amazoncorretto:21-alpine-jdk

EXPOSE 9100

ADD ./target/oauth-service-0.0.1-SNAPSHOT.jar /app/oauth-service.jar

ENTRYPOINT ["java", "-jar", "/app/oauth-service.jar"]

