FROM amazoncorretto:21-alpine-jdk

EXPOSE 8005
ADD ./target/items-service-0.0.1-SNAPSHOT.jar /app/items-service.jar

ENTRYPOINT ["java", "-jar", "/app/items-service.jar"]

