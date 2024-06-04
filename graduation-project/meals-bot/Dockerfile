FROM bellsoft/liberica-openjdk-alpine-musl:17.0.1-12 as builder
COPY ./target/meals-bot-1.0.0.jar /app/mvc.jar
EXPOSE 8383
ENTRYPOINT ["java", "-jar", "app/mvc.jar"]

