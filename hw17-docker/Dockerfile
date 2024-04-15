FROM bellsoft/liberica-openjdk-alpine-musl:17.0.1-12
COPY ./target/hw17-docker-1.0.jar /app/mvc.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app/mvc.jar"]
