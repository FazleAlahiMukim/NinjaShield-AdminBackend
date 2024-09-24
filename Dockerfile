FROM maven:3.8.4-eclipse-temurin-21 as builder
WORKDIR /app
COPY . /app
RUN mvn clean package

FROM eclipse-temurin:21
WORKDIR /opt/app
COPY --from=builder /app/target/my-app.jar /opt/app/my-app.jar
CMD ["java", "-jar", "/opt/app/my-app.jar"]
