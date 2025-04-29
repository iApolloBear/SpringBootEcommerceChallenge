FROM openjdk:21-slim AS builder

WORKDIR /app

LABEL author="Aldo Espinosa <aldoespinosaperez1@gmail.com>"
LABEL mantainer="Aldo Espinosa <aldoespinosaperez1@gmail.com>"

COPY ./pom.xml .
COPY ./.mvn ./.mvn
COPY ./mvnw .

RUN ./mvnw dependency:go-offline

COPY ./src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:21-slim

WORKDIR /app
COPY --from=builder /app/target/ecommerce-challenge-0.0.1-SNAPSHOT.jar ./app.jar
CMD ["java", "-jar", "app.jar"]