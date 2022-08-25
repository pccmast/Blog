#FROM openjdk:8u212-alpine
FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/lzp-spring-blog-0.0.1.jar /app:

EXPOSE 8808

ENTRYPOINT ["java", "-jar"]

CMD ["lzp-spring-blog-0.0.1.jar"]

