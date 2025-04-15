FROM amazoncorretto:21-alpine-jdk

EXPOSE 8761
ADD ./target/discovery-server-0.0.1-SNAPSHOT.jar /app/discovery-server.jar

ENTRYPOINT ["java", "-jar", "/app/discovery-server.jar"]

