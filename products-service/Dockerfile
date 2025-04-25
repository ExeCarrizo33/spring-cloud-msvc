FROM amazoncorretto:21-alpine-jdk

ADD ./target/products-service-0.0.1-SNAPSHOT.jar /app/products-service.jar

ENTRYPOINT ["java", "-jar", "/app/products-service.jar"]

