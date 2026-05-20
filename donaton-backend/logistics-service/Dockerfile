# Usa Maven para compilar el código dentro de Docker
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Usa Java para ejecutar el archivo compilado
FROM eclipse-temurin:17-jre
# Nota: evita errores por imágenes inexistentes/metadata; si falla en tu entorno, reemplaza por openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
