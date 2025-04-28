FROM amazoncorretto:21-alpine-jdk

EXPOSE 8090

ADD ./target/gateway-server-0.0.1-SNAPSHOT.jar /app/gateway-server.jar

ENTRYPOINT ["java", "-jar", "/app/gateway-server.jar"]
