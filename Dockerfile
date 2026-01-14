FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENV SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/proy_lvl
ENV SPRING_DATASOURCE_USERNAME=jira_user
ENV SPRING_DATASOURCE_PASSWORD=jira_password

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]