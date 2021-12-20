FROM maven:3.8.4-openjdk-8-slim as builder
WORKDIR /epic-dev-tools

COPY ./pom.xml .
COPY ./src/ ./src/

RUN --mount=type=cache,target=/root/.m2 ["mvn", "-Djar.finalName=tool", "package"]

FROM openjdk:8-jre-slim-buster

WORKDIR /epic-dev-tools

COPY --from=builder /epic-dev-tools/target/*.jar ./tool.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "tool.jar"]