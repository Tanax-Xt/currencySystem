FROM eclipse-temurin:23-jdk AS builder

WORKDIR /app

COPY . .

RUN ./gradlew clean build -x test

FROM eclipse-temurin:23-jre

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

CMD ["java", "-jar", "app.jar"]
