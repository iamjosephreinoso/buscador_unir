#
# Build del proyecto (Multi-Stage)
# --------------------------------
#
# Usamos una imagen de Maven para hacer build de proyecto con Java
FROM maven:3.9.6-eclipse-temurin-21 AS build
COPY . .
RUN mvn clean package -DskipTests

# Usamos una imagen de Openjdk
FROM openjdk:21
EXPOSE 8081
COPY --from=build /target/ms-buscador-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
