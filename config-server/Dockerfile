FROM amazoncorretto:21-alpine-jdk

EXPOSE 8888
ADD ./target/config-server-0.0.1-SNAPSHOT.jar /app/config-server.jar

ENTRYPOINT ["java", "-jar", "/app/config-server.jar"]

