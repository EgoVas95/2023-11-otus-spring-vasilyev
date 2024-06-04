FROM bellsoft/liberica-openjdk-alpine-musl:17.0.1-12 as builder
COPY ./target/eureka-server-1.0.0.jar /app/mvc.jar
EXPOSE 9888
ENTRYPOINT ["java", "-jar", "app/mvc.jar"]

