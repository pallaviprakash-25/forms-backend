# Build using maven
FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Run on jdk17
FROM openjdk:17.0.1-jdk-slim

WORKDIR /app
COPY --from=build /app/target/forms-*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java -jar app.jar"]
