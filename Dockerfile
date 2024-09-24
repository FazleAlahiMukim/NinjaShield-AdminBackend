FROM maven:3.8.4-eclipse-temurin-21-alpine AS build


WORKDIR /app


COPY pom.xml .
RUN mvn dependency:go-offline -B


COPY src ./src


RUN mvn clean install -DskipTests


FROM eclipse-temurin:21-jdk-alpine


WORKDIR /app


COPY --from=build /app/target/*.jar app.jar


EXPOSE 8080


ENTRYPOINT ["java", "-jar", "/app/app.jar"]
