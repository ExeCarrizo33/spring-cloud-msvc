FROM amazoncorretto:21-alpine-jdk

EXPOSE 9411
COPY zipkin-server-3.5.0-exec.jar /app/zipkin-server.jar

WORKDIR /app
ENTRYPOINT ["java", "-jar", "zipkin-server.jar"]
